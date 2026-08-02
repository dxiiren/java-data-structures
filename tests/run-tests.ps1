# tests\run-tests.ps1 -- golden-output test harness.
#
# For every golden tests\expected\<Name>.txt: compiles <Name>.java (same-folder
# dependencies via -sourcepath, exactly like `just build`), runs it with stdin from
# sample-inputs\<Name>.txt when it exists (else < NUL for the no-input demo), and
# compares stdout against the golden with CRLF/LF normalized. A non-zero java exit
# code is a FAIL even when stdout matches. One line per program, summary at the
# end, exit 1 on any failure. Program source is never touched -- the goldens pin
# behavior as-is, coursework quirks included.
#
# Run it via `just test`. stdin is redirected inside cmd on purpose -- the
# PowerShell 5.1 pipe injects a UTF-8 BOM into the first stdin line, and several
# programs' first read is Integer.parseInt.
#
# The programs are compiled and run CONCURRENTLY, which needed one real change
# first. `just build` puts every program's classes in one flat out\, and that is
# fine when only one compile runs at a time -- but Proto5..Proto8 all depend on
# Job.java, and the ADTs all depend on Node.java, so concurrent compiles would
# race to write the SAME out\Job.class and out\Node.class. A half-written class
# file is a corrupt-looking failure that would come and go between runs.
#
# So the harness compiles each program into its own out\<Name>\ instead, and runs
# it with that directory alone on the classpath. Each program's dependencies are
# rebuilt privately -- a few extra Job.class copies, which cost nothing -- and no
# two workers ever write the same path. The flat out\ that `just build`,
# `just build-all` and `just run` use is untouched; `just clean` still clears
# both, since it removes out\ wholesale.
#
# Two things the parallel block must NOT rely on:
#   * the current directory -- a runspace does not reliably inherit the caller's
#     location, so every path handed to javac or cmd here is absolute;
#   * output ordering -- workers finish out of order, so results are collected as
#     objects and printed sorted by name afterwards. The printed output is byte
#     for byte what the serial version produced.
#
# The programs themselves write no files at runtime (see CLAUDE.md), so running
# them at the same time is safe. If you add one that does, give it a unique path
# or exclude it from the parallel set.
[CmdletBinding()]
param(
    # Compiling is the bulk of the work and is CPU-bound, so this tracks core
    # count. Override to 1 for the old serial behaviour when debugging.
    [int]$ThrottleLimit = [Environment]::ProcessorCount
)

if (-not (Get-Command javac -ErrorAction SilentlyContinue)) {
    Write-Host 'JDK (javac) not found on PATH.  -> Run setup.ps1 first:  pwsh ./setup.ps1' -ForegroundColor Red
    exit 1
}

$repo = Split-Path -Parent $PSScriptRoot
Set-Location $repo
if (-not (Test-Path out)) { New-Item -ItemType Directory out | Out-Null }

$goldens = @(Get-ChildItem (Join-Path $PSScriptRoot 'expected') -Filter *.txt | Sort-Object Name)
if ($goldens.Count -eq 0) {
    Write-Host 'No goldens found in tests\expected\ -- nothing to test.' -ForegroundColor Red
    exit 1
}

$srcDirs = 'adts', 'sorting-searching', 'scheduling-prototypes', 'misc' | ForEach-Object { Join-Path $repo $_ }

$results = $goldens | ForEach-Object -ThrottleLimit $ThrottleLimit -Parallel {
    $repo = $using:repo
    $g = $_
    $name = $g.BaseName

    $src = Get-ChildItem $using:srcDirs -Filter "$name.java" | Select-Object -First 1
    if (-not $src) {
        return [pscustomobject]@{ Name = $name; Ok = $false; Line = "[FAIL] $name -- golden exists but $name.java does not" }
    }

    # Private class output dir -- this is what makes concurrent compiles safe.
    $classDir = Join-Path $repo "out\$name"
    New-Item -ItemType Directory -Force $classDir | Out-Null

    javac -d $classDir -sourcepath $src.DirectoryName $src.FullName 2>&1 | Out-Null
    if ($LASTEXITCODE -ne 0) {
        return [pscustomobject]@{ Name = $name; Ok = $false; Line = "[FAIL] $name -- compile error" }
    }

    $actualPath = "out\$name.actual.txt"
    $actualFull = Join-Path $repo $actualPath
    $inputPath = Join-Path $repo "sample-inputs\$name.txt"

    # Absolute paths, and the redirection still happens inside cmd rather than
    # through a PowerShell pipe -- the pipe injects a UTF-8 BOM into the first
    # stdin line and several programs' first read is Integer.parseInt. cmd
    # strips the outermost pair of quotes, hence the extra wrapping pair.
    if (Test-Path $inputPath) {
        cmd /c "java -cp `"$classDir`" $name < `"$inputPath`" > `"$actualFull`""
    } else {
        cmd /c "java -cp `"$classDir`" $name < NUL > `"$actualFull`""
    }
    if ($LASTEXITCODE -ne 0) {
        return [pscustomobject]@{ Name = $name; Ok = $false; Line = "[FAIL] $name -- exit code $LASTEXITCODE" }
    }

    $expected = ([IO.File]::ReadAllText($g.FullName)) -replace "`r`n", "`n"
    $actual = ([IO.File]::ReadAllText($actualFull)) -replace "`r`n", "`n"
    if ($expected -eq $actual) {
        return [pscustomobject]@{ Name = $name; Ok = $true; Line = "[PASS] $name" }
    }

    $expLines = $expected -split "`n"
    $actLines = $actual -split "`n"
    $max = [Math]::Max($expLines.Count, $actLines.Count)
    $firstDiff = 0
    for ($i = 0; $i -lt $max; $i++) {
        $e = if ($i -lt $expLines.Count) { $expLines[$i] } else { $null }
        $a = if ($i -lt $actLines.Count) { $actLines[$i] } else { $null }
        if ($e -cne $a) { $firstDiff = $i + 1; break }
    }
    [pscustomobject]@{ Name = $name; Ok = $false; Line = "[FAIL] $name -- first difference at line $firstDiff (see $actualPath vs tests\expected\$name.txt)" }
}

# Sorted, so the report reads identically to the serial harness no matter what
# order the workers happened to finish in.
$pass = 0
$fail = 0
foreach ($r in ($results | Sort-Object Name)) {
    if ($r.Ok) {
        Write-Host $r.Line -ForegroundColor Green
        $pass++
    } else {
        Write-Host $r.Line -ForegroundColor Red
        $fail++
    }
}

Write-Host ''
$total = $goldens.Count
if ($fail -eq 0) {
    Write-Host "test summary: $pass/$total PASS, 0 FAIL" -ForegroundColor Green
    exit 0
} else {
    Write-Host "test summary: $pass/$total PASS, $fail FAIL" -ForegroundColor Red
    exit 1
}
