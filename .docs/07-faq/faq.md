# FAQ

> **TL;DR** — Why the repo looks the way it does: preservation-first coursework, four
> self-contained folders, and the finished scheduler living in a sibling repo. The
> second-Scanner defect and the `adts/` `==`-on-Objects bug were both fixed in 2026 (each
> in its own deliberate change); the remaining quirks (e.g. the idle-gap crash) stay.

## Why aren't all the bugs fixed (idle-gap crash, etc.)?

This is preserved university coursework — the value is the learning progression, not
production quality. Behavior changes happen only when explicitly asked for, in their own
commits. Two have happened so far: the double-Scanner defect (and Proto8's `SIZE =N` debug
print) in 2026, and — also in 2026 — `LinkedList.search`/`deleteNode`'s `==`/`!=`
reference-identity comparison, replaced with `Objects.equals(...)` after the new
`AdtsDemo` driver exposed it silently dropping non-interned String / uncached Integer
matches. All 12 runnable programs are now golden-tested. The remaining quirks are
documented instead ([common-issues](../06-troubleshooting/common-issues.md)).

## Why are there five nearly identical SortingAndSearching programs?

That is the point of the set: the same three algorithms re-implemented across element types
(int → String → object) and directions (ascending → descending). The diffs between versions
ARE the coursework.

## Why keep all four Proto files when Proto8 supersedes them?

Same reason — each prototype adds one feature (queue sorting, waiting time, averages,
output cleanup). The chain documents how the final scheduler was reached.

## Where is the finished scheduler?

The finished variant — a linked-list-based `mainApp` with a Job-typed sorting `LinkedList` —
formerly lived in a standalone repo that has since been retired. These prototypes are its
documented ancestors, and the lineage is preserved here: this repo's `Job.java` and
`Node.java` are its earlier variants, kept because Proto5–8 and the generic ADTs compile
against them.

## Why did `adts/` have no runnable program?

The archive contained no driver for the ADT family; the classes were the library the course
exercises built on. That was the repo's one real coverage gap, closed in 2026 by adding
`AdtsDemo` — a small, deterministic driver (no stdin, no randomness, no identity-hash
output) that exercises `LinkedList`/`Stack`/`Queue` end to end and is now golden-tested
like every other program (`just run AdtsDemo` / `just test`).

## Why no Maven/Gradle/JUnit?

The coursework was written for `javac` + TextPad. The justfile recipes reproduce that
faithfully; adding a build tool or test framework would be modernization, which this repo
deliberately avoids.

## Can I add a new program?

Yes — see [workflow.md](../03-development/workflow.md): put it in the right topic folder,
commit a sample input if it reads stdin (and supports redirection), update the README
catalog and input-formats reference.

## Didn't `sample-inputs\` used to be missing files for four programs?

Yes — `ProgramApp` and `SortingAndSearchingV1/V2/V4` could not consume redirected stdin
(their second `Scanner` hit EOF), so committing samples would just have crashed them.
Their search methods were fixed in 2026 to reuse main's Scanner, and every stdin-reading
program now has a committed sample file
([input-formats.md](../05-reference/input-formats.md)).

## Related docs

| Doc | Why |
| --- | --- |
| [../01-overview/project-overview.md](../01-overview/project-overview.md) | Course context |
| [../06-troubleshooting/common-issues.md](../06-troubleshooting/common-issues.md) | The quirks referenced above |
