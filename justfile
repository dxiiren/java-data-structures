# Java Data Structures justfile — development recipes

set shell := ["powershell.exe", "-NoProfile", "-Command"]

# List available recipes
default:
    @just --list

# ─── Guards ───────────────────────────────────────────────

# JDK — installed by setup.ps1; needed by build/run.
[private]
_require-jdk:
    @if (-not (Get-Command javac -ErrorAction SilentlyContinue)) { Write-Error "JDK (javac) not found on PATH.`n  -> Run setup.ps1 first:  pwsh ./setup.ps1"; exit 1 }

# ─── Catalog ─────────────────────────────────────────────

# List every runnable program (class with a main method), one per line.
list:
    @Get-ChildItem -Recurse -Filter *.java | Select-String -List -Pattern 'static\s+void\s+main' | ForEach-Object { [IO.Path]::GetFileNameWithoutExtension($_.Path) } | Sort-Object

# ─── Build & run ─────────────────────────────────────────

# Compile one program (same-folder dependencies included) into out\.  e.g. just build Proto8
build name: _require-jdk
    $f = Get-ChildItem -Recurse -Filter "{{name}}.java" | Select-Object -First 1; if (-not $f) { Write-Error "No source file {{name}}.java in this repo. Run 'just list' for the catalog."; exit 1 }; if (-not (Test-Path out)) { New-Item -ItemType Directory out | Out-Null }; javac -d out -sourcepath $f.DirectoryName $f.FullName; exit $LASTEXITCODE

# Compile every folder; fail on first error; print a PASS/FAIL summary.
build-all: _require-jdk
    if (-not (Test-Path out)) { New-Item -ItemType Directory out | Out-Null }; foreach ($dir in 'adts','sorting-searching','scheduling-prototypes','misc') { & javac -d out (Get-ChildItem "$dir\*.java").FullName; if ($LASTEXITCODE -eq 0) { Write-Host "[PASS] $dir" -ForegroundColor Green } else { Write-Host "[FAIL] $dir" -ForegroundColor Red; exit 1 } }; Write-Host "All 4 folders compiled (17 files)." -ForegroundColor Green

# PowerShell's `Get-Content |` pipe prepends a UTF-8 BOM to the first stdin line (crashes
# apps whose first read is numeric); cmd redirection passes the file bytes through untouched.
# Build + run one program — stdin from sample-inputs\<name>.txt when it exists.  e.g. just run Proto8
run name: (build name)
    if (Test-Path "sample-inputs\{{name}}.txt") { cmd /c "java -cp out {{name}} < sample-inputs\{{name}}.txt" } else { Write-Host "[INFO] No sample-inputs\{{name}}.txt -- running directly (programs that read stdin will wait for typed input)." -ForegroundColor Yellow; java -cp out {{name}} }

# Build + run one program with YOUR OWN typed input (interactive).
run-interactive name: (build name)
    java -cp out {{name}}

# The harness (tests\run-tests.ps1) rebuilds each covered program, feeds its committed
# sample input (or no stdin for ForEachExample1), and diffs stdout against tests\expected\
# — CRLF-normalized, one PASS/FAIL line per program, non-zero exit codes fail, exit 1 on
# any failure. The 4 double-Scanner programs (ProgramApp, V1/V2/V4) stay interactive-only.
# Run the golden-output test suite over every program with a committed expected output.
test:
    & 'tests\run-tests.ps1'; exit $LASTEXITCODE

# Remove compiled classes.
clean:
    if (Test-Path out) { Remove-Item -Recurse -Force out }

# ─── Tools ───────────────────────────────────────────────

# Launch Claude Code with all permissions — Sonnet (latest)
claudex:
    claude --dangerously-skip-permissions --model sonnet

# Launch Claude Code with all permissions — Opus (latest)
claudeo:
    claude --dangerously-skip-permissions --model opus

# Launch Claude Code with all permissions — Haiku (latest)
claudeh:
    claude --dangerously-skip-permissions --model haiku
