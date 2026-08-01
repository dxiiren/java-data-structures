# FAQ

> **TL;DR** — Why the repo looks the way it does: preservation-first coursework, four
> self-contained folders, some programs interactive only, and the finished scheduler living
> in a sibling repo.

## Why aren't the bugs fixed (double Scanner, `==` on Objects, idle-gap crash)?

This is preserved university coursework — the value is the learning progression, not
production quality. Behavior changes happen only when explicitly asked for, in their own
commits. The known quirks are documented instead
([common-issues](../06-troubleshooting/common-issues.md)).

## Why are there five nearly identical SortingAndSearching programs?

That is the point of the set: the same three algorithms re-implemented across element types
(int → String → object) and directions (ascending → descending). The diffs between versions
ARE the coursework.

## Why keep all four Proto files when Proto8 supersedes them?

Same reason — each prototype adds one feature (queue sorting, waiting time, averages,
output cleanup). The chain documents how the final scheduler was reached.

## Where is the finished scheduler?

Published separately as
[`java-linked-list-sorting`](https://github.com/dxiiren/java-linked-list-sorting) —
a linked-list-based `mainApp` with a Job-typed sorting `LinkedList`. This repo's `Job.java`
and `Node.java` are its earlier variants, kept because Proto5–8 and the generic ADTs
compile against them.

## Why does `adts/` have no runnable program?

The archive contained no driver for the ADT family; the classes were the library the course
exercises built on. Adding a demo `main` would be new code, not preservation. `just build
LinkedList` proves the family compiles.

## Why no Maven/Gradle/JUnit?

The coursework was written for `javac` + TextPad. The justfile recipes reproduce that
faithfully; adding a build tool or test framework would be modernization, which this repo
deliberately avoids.

## Can I add a new program?

Yes — see [workflow.md](../03-development/workflow.md): put it in the right topic folder,
commit a sample input if it reads stdin (and supports redirection), update the README
catalog and input-formats reference.

## Why is `sample-inputs\` missing files for four programs?

`ProgramApp` and `SortingAndSearchingV1/V2/V4` cannot consume redirected stdin (second
`Scanner` hits EOF) — a committed sample would just crash them. They run interactively;
example keyboard sequences live in
[input-formats.md](../05-reference/input-formats.md).

## Related docs

| Doc | Why |
| --- | --- |
| [../01-overview/project-overview.md](../01-overview/project-overview.md) | Course context |
| [../06-troubleshooting/common-issues.md](../06-troubleshooting/common-issues.md) | The quirks referenced above |
