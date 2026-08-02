# TL;DR — every doc in 30 seconds

## [01-overview/project-overview.md](01-overview/project-overview.md)

17 plain-Java console programs from CSC248 (Data Structures, UiTM, ~2021), imported from
the university archive into four topic folders: generic linked list / stack / queue ADTs,
five progressive sorting-and-searching programs, four SJF CPU-scheduling prototypes, and a
for-each demo. One matric number was stripped from a header; everything else is preserved
as written. The finished SJF scheduler the prototypes led to formerly lived in a standalone
repo (since retired); these prototypes are its documented ancestors, and this repo's
`Job`/`Node` are its earlier variants — the lineage is preserved here.

## [01-overview/architecture.md](01-overview/architecture.md)

No build tool — each folder compiles on its own via `javac -sourcepath` into one shared
`out\` (class names must stay unique repo-wide). The ADT family is an inheritance chain
(`Stack`/`Queue` extend a generic `Object`-payload `LinkedList`), the V1–V5 set
re-implements bubble/insertion/binary-search across types and directions, and Proto5→8
each add one scheduler feature (SJF sort, wait times, averages, clean output).

## [02-setup/getting-started.md](02-setup/getting-started.md)

`pwsh ./setup.ps1` once (idempotent; installs Git, Node, Claude CLI, uv/Python, Temurin
JDK, just, gh), reopen PowerShell, then `just list` → `just build-all` → `just run Proto8`.
A good Proto8 run ends with average turn-around and waiting times.

## [03-development/workflow.md](03-development/workflow.md)

Edit → `just run <name>` → keep `sample-inputs\<name>.txt` and the input-formats doc in
sync with any Scanner change → `just build-all` + `just test` (11/11 golden diffs) +
`/lint-check` before a PR. Preservation
first: no modernization, no new tooling, unique class names, Conventional Commits with
repo-local gmail identity.

## [04-deployment/deployment.md](04-deployment/deployment.md)

There is no deployment: no CI/CD, no server. Pushing to `main` is the distribution;
`setup.ps1` + `just build-all` reproduce the runtime anywhere.

## [05-reference/commands.md](05-reference/commands.md)

The recipe table (`list`, `build <name>`, `build-all`, `run <name>`, `run-interactive
<name>`, `test` — golden-output suite over all 11 runnable programs, `clean`, `claudex`)
and why `run` redirects via `cmd /c` instead of a PowerShell pipe (BOM injection crashes
numeric first reads).

## [05-reference/input-formats.md](05-reference/input-formats.md)

Exact stdin order per program. Proto liveness rules: first job arrives at time 1, no idle
gaps. Every stdin-reading program has a committed sample file (the second-`Scanner`
defect that kept `ProgramApp`, V1, V2, V4 interactive-only was fixed in 2026).

## [05-reference/project-layout.md](05-reference/project-layout.md)

Annotated tree: four source folders, `sample-inputs\`, `tests\` (golden harness +
expected outputs), git-ignored `out\`, the kit files, and the rules the layout encodes
(per-folder deps, unique names, which programs have samples).

## [06-troubleshooting/common-issues.md](06-troubleshooting/common-issues.md)

Real verify-run symptoms: the second-Scanner `NoSuchElementException` under redirection
(fixed in 2026 — kept as history), the Proto idle-gap `IndexOutOfBoundsException`, the
PowerShell-pipe BOM crash, the accepted `[serial]` warning baseline, exact-name `just run`
errors, and the shared-`out\` class-name clash.

## [07-faq/faq.md](07-faq/faq.md)

Which bugs stay unfixed vs which were fixed (the 2026 second-Scanner fix), why five
near-identical V-versions and four Protos exist (the progression is the point), where the
finished scheduler lives, and why `adts/` has no main.
