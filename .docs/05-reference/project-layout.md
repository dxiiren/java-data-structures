# Project layout

> **TL;DR** — Four topic folders of default-package Java, one `sample-inputs\` folder of
> canned stdin, one git-ignored `out\` for classes, plus the standard kit (justfile,
> setup.ps1, .docs, .claude).

```
java-data-structures/
  adts/
    LinkedList.java          # generic singly linked list (Object payload) + EmptyListException
    Node.java                # list node: Object data + next
    Stack.java               # push/pop/peek, extends LinkedList
    Queue.java               # enqueue/dequeue/getFront/getEnd, extends LinkedList
  sorting-searching/
    ProgramApp.java          # 10 ints -> bubble sort -> binary search
    SortingAndSearchingV1.java   # ints, ascending
    SortingAndSearchingV2.java   # Strings, ascending
    SortingAndSearchingV3.java   # Student objects, ascending
    SortingAndSearchingV4.java   # ints + Strings, descending
    SortingAndSearchingV5.java   # Student objects, descending
    Student.java             # data class (name, number) for V3/V5
  scheduling-prototypes/
    Proto5.java              # clock-tick trace, FCFS
    Proto6.java              # + waiting time + SJF queue sort
    Proto7.java              # + average executing/waiting times
    Proto8.java              # + cleaned output (turn-around label)
    Job.java                 # data class (name, cpuTime, arrivalTime, waitingTime)
  misc/
    ForEachExample1.java     # for-each loop demo (no input)
  sample-inputs/             # <ProgramName>.txt — canned stdin per stdin-reading program
  tests/                     # golden-output harness (`just test`)
    run-tests.ps1            #   builds + runs every covered program, diffs stdout + exit code
    expected/                #   7 goldens named <ProgramName>.txt
  out/                       # compiled .class files (git-ignored)
  .docs/                     # this documentation set
  .claude/                   # skills, hooks, settings, memory
  justfile                   # list / build / build-all / run / run-interactive / test / clean
  setup.ps1                  # idempotent toolchain bootstrap
  CLAUDE.md                  # AI-assistant project brief
  .mcp.json.stub             # committed MCP config template (real .mcp.json git-ignored)
```

## Rules encoded in this layout

- A program's compile-time dependencies live in **its own folder** — `javac -sourcepath
  <folder>` resolves them; nothing references across folders.
- All classes share the default package and one `out\`: class names are unique repo-wide.
- A `sample-inputs\<ProgramName>.txt` exists for every program that supports redirected
  stdin (V3, V5, Proto5–8). `ForEachExample1` reads nothing; `ProgramApp`/V1/V2/V4 are
  interactive only (second-`Scanner` EOF — see [input-formats.md](input-formats.md)) and
  intentionally have no sample file.
- Generated things (`out\`, `.mcp.json`, `.claude/settings.local.json`,
  `.claude/workspace/`) are git-ignored; everything else is committed.

## Related docs

| Doc | Why |
| --- | --- |
| [../01-overview/architecture.md](../01-overview/architecture.md) | What each family does |
| [commands.md](commands.md) | The recipes that operate on this layout |
