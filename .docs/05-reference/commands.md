# Commands

> **TL;DR** — Everything is a `just` recipe. `list` shows the 12 runnable programs,
> `build-all` compiles the four folders, `run <name>` builds one program and feeds it its
> canned sample input.

## Daily recipes

| Recipe | What it does | Notes |
| --- | --- | --- |
| `just` | List all recipes | |
| `just list` | Enumerate runnable programs (classes with `main`) | 12 programs |
| `just build <name>` | Compile one program into `out\` | `-sourcepath` pulls same-folder deps (`Job`, `Student`, `Node`, `LinkedList`) |
| `just build-all` | Compile `adts`, `sorting-searching`, `scheduling-prototypes`, `misc` | Fails on first error; `[PASS]`/`[FAIL]` per folder |
| `just run <name>` | Build + run with `sample-inputs\<name>.txt` as stdin | Falls back to direct (interactive) run when no sample file exists |
| `just run-interactive <name>` | Build + run with your own typed input | Prompt order in [input-formats.md](input-formats.md) |
| `just test` | Golden-output suite via `tests\run-tests.ps1` | All 12 runnable programs (Proto5–8, ProgramApp, V1–V5, ForEachExample1, AdtsDemo); diffs stdout vs `tests\expected\` (CRLF-normalized), non-zero exit codes fail, exit 1 on any fail |
| `just clean` | Delete `out\` | |
| `just claudex` / `claudeo` / `claudeh` | Claude Code, all permissions (Sonnet / Opus / Haiku) | |

## Why `run` goes through `cmd /c`

The recipe is `cmd /c "java -cp out <name> < sample-inputs\<name>.txt"` on purpose: piping
with PowerShell 5.1 (`Get-Content file | java ...`) injects a UTF-8 BOM into the first stdin
line, and several programs' first read is `Integer.parseInt(...)` — the BOM crashes them
with `NumberFormatException`. Don't "simplify" the redirect into a pipe.

## Underlying commands (when just is unavailable)

```powershell
javac -d out -sourcepath scheduling-prototypes scheduling-prototypes\Proto8.java
cmd /c "java -cp out Proto8 < sample-inputs\Proto8.txt"
```

## Related docs

| Doc | Why |
| --- | --- |
| [input-formats.md](input-formats.md) | What each program reads from stdin |
| [project-layout.md](project-layout.md) | Where everything lives |
| [../06-troubleshooting/common-issues.md](../06-troubleshooting/common-issues.md) | When a recipe fails |
