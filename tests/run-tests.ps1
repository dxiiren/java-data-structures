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

$pass = 0
$fail = 0
foreach ($g in $goldens) {
    $name = $g.BaseName
    $src = Get-ChildItem 'adts', 'sorting-searching', 'scheduling-prototypes', 'misc' -Filter "$name.java" | Select-Object -First 1
    if (-not $src) {
        Write-Host "[FAIL] $name -- golden exists but $name.java does not" -ForegroundColor Red
        $fail++
        continue
    }

    javac -d out -sourcepath $src.DirectoryName $src.FullName 2>&1 | Out-Null
    if ($LASTEXITCODE -ne 0) {
        Write-Host "[FAIL] $name -- compile error" -ForegroundColor Red
        $fail++
        continue
    }

    $actualPath = "out\$name.actual.txt"
    if (Test-Path "sample-inputs\$name.txt") {
        cmd /c "java -cp out $name < sample-inputs\$name.txt > $actualPath"
    } else {
        cmd /c "java -cp out $name < NUL > $actualPath"
    }
    if ($LASTEXITCODE -ne 0) {
        Write-Host "[FAIL] $name -- exit code $LASTEXITCODE" -ForegroundColor Red
        $fail++
        continue
    }

    $expected = ([IO.File]::ReadAllText($g.FullName)) -replace "`r`n", "`n"
    $actual = ([IO.File]::ReadAllText((Join-Path $repo $actualPath))) -replace "`r`n", "`n"
    if ($expected -eq $actual) {
        Write-Host "[PASS] $name" -ForegroundColor Green
        $pass++
    } else {
        $expLines = $expected -split "`n"
        $actLines = $actual -split "`n"
        $max = [Math]::Max($expLines.Count, $actLines.Count)
        $firstDiff = 0
        for ($i = 0; $i -lt $max; $i++) {
            $e = if ($i -lt $expLines.Count) { $expLines[$i] } else { $null }
            $a = if ($i -lt $actLines.Count) { $actLines[$i] } else { $null }
            if ($e -cne $a) { $firstDiff = $i + 1; break }
        }
        Write-Host "[FAIL] $name -- first difference at line $firstDiff (see $actualPath vs tests\expected\$name.txt)" -ForegroundColor Red
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
