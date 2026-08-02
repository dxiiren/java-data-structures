---
name: lint-check
description: Use when the developer says 'lint check', 'run lint', 'check lint', 'run the quality suite', or 'lint everything' — runs the quality gates this repo has (javac with -Xlint:all warnings per folder, plus a build-all/run smoke) and reports pass/fail per layer.
model: sonnet
---

# lint-check — Quality suite (javac -Xlint · build-all/run smoke)

This repo has no ESLint/Prettier/Checkstyle — the honest quality layers for a plain-Java
program collection are the compiler's own warnings and a run-to-completion smoke test on
representative programs. Run both and report pass/fail per layer.

## Trigger

When the developer says any of: "lint check", "run lint", "check lint",
"run the quality suite", "lint everything".

---

## What to Do

Run each layer and record its result. Run them independently so one failure doesn't
hide the others.

### 1 — Compiler warnings (`javac -Xlint:all`, per folder)

Each top-level folder is its own compilation unit (all classes are in the default package;
same-folder files are compile dependencies):

```powershell
foreach ($dir in 'adts','sorting-searching','scheduling-prototypes','misc') {
    javac -Xlint:all -d out (Get-ChildItem "$dir\*.java").FullName
}
```

Pass = exit 0 for every folder AND no warnings beyond the known baseline. New warnings
(unchecked, rawtypes, fallthrough, ...) are findings — list each with file:line. Fix at
the root cause, never by suppressing with `@SuppressWarnings` just to go quiet.

> Known baseline (accepted, preserved coursework — do NOT "fix" it): exactly one warning,
> `adts\LinkedList.java:166: warning: [serial] serializable class EmptyListException has
> no definition of serialVersionUID`. Anything else is a regression introduced by the branch.

### 2 — Build/run smoke

```powershell
just build-all
just run SortingAndSearchingV3
just run Proto8
```

Pass = all exit 0, no stack trace, and each program prints its closing output
(V3: the Found/Not Found verdict; Proto8: the average turn-around and waiting times).
Every stdin-reading program has a committed sample input (the second-`Scanner` EOF defect
in `ProgramApp`/`V1`/`V2`/`V4` was fixed in 2026), so `just test` covers all 12 runnable
programs.
This is the repo's only executable verification — treat a stack trace as a FAIL even if
the exit code is masked.

---

## Reporting back

Report a per-layer table, then an overall verdict:

```
LAYER      TOOL                            STATUS
warnings   javac -Xlint:all (4 folders)    PASS | FAIL (N new warnings)
smoke      just build-all + 2 sample runs  PASS | FAIL (exit codes / first stack-trace line)
OVERALL: PASS | FAIL
```

---

## Notes

- Run from the **repo root** — recipes and folder paths assume it.
- `just run <name>` feeds `sample-inputs\<name>.txt` via cmd redirection; a program that
  exhausts its sample input dies with `NoSuchElementException` — that counts as FAIL.
- There is no auto-fix layer here — every fix is a source edit; re-run the layer after.
- Don't bolt on Checkstyle/PMD/SpotBugs uninvited — this is preserved uni coursework;
  propose new tooling to the developer instead of adding it inside a lint run.
