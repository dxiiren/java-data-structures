# Getting started

> **TL;DR** — `pwsh ./setup.ps1` once (installs JDK, just, Git, Node, uv, Claude CLI),
> reopen PowerShell, then `just list` → `just build-all` → `just run Proto8`.

## 1. One-time machine setup

From the repo root:

```powershell
pwsh ./setup.ps1
```

Idempotent — safe to re-run any time; every step prints `[OK]` when already satisfied. It
installs (only if missing): Git, Node.js LTS (for the Claude CLI), the Claude Code CLI, uv +
a managed Python (for `.claude` tooling), **Temurin JDK 21**, just, and the GitHub CLI, then
seeds `.mcp.json` from the committed stub.

Close and reopen PowerShell afterwards so PATH updates land.

## 2. First build and run

```powershell
just list          # 11 runnable programs
just build-all     # compiles adts, sorting-searching, scheduling-prototypes, misc
just run Proto8    # SJF scheduler prototype against sample-inputs\Proto8.txt
just run SortingAndSearchingV3
```

A successful `just run Proto8` ends with the average turn-around and waiting times; a
successful V3 run ends with a `Found !` verdict.

## 3. Running with your own input

```powershell
just run-interactive SortingAndSearchingV1
```

Type values in the exact prompt order — see
[`../05-reference/input-formats.md`](../05-reference/input-formats.md). Note that
`ProgramApp`, `SortingAndSearchingV1`, `V2`, and `V4` are **interactive only** (they open a
second `Scanner`, which breaks redirected stdin) — `just run` on them waits for your typed
input by design. For the Proto programs keep the CPU busy (no arrival gaps) or they crash;
the input contracts doc explains why.

## Verification checklist

| Check | Expect |
| --- | --- |
| `javac -version` | 11 or newer |
| `just build-all` | four `[PASS]` lines, exit 0 |
| `just run Proto8` | trace ending in two averages, no stack trace |
| re-run `pwsh ./setup.ps1` | all `[OK]`, no installs |

## Related docs

| Doc | Why |
| --- | --- |
| [../03-development/workflow.md](../03-development/workflow.md) | Day-2 loop and conventions |
| [../05-reference/commands.md](../05-reference/commands.md) | Every just recipe |
| [../06-troubleshooting/common-issues.md](../06-troubleshooting/common-issues.md) | If a step above fails |
