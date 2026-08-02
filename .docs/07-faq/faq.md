# FAQ

> **TL;DR** — Why the repo looks the way it does: preservation-first coursework, four
> self-contained folders, and the finished scheduler living in a sibling repo. The
> second-Scanner defect was fixed in 2026; the remaining quirks stay.

## Why aren't the bugs fixed (`==` on Objects, idle-gap crash)?

This is preserved university coursework — the value is the learning progression, not
production quality. Behavior changes happen only when explicitly asked for, in their own
commits: the double-Scanner defect (and Proto8's `SIZE =N` debug print) got exactly such
a deliberate fix in 2026, so all 11 runnable programs are now golden-tested. The remaining
quirks are documented instead ([common-issues](../06-troubleshooting/common-issues.md)).

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
