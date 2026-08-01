# Project overview

> **TL;DR** — 17 plain-Java console programs from CSC248 (Data Structures, UiTM, semester 3,
> ~2021): generic linked list / stack / queue ADTs, five sorting-and-searching exercises, and
> four SJF CPU-scheduling prototypes. Imported from the university archive, organized into
> four topic folders, preserved as written.

## What this is

Coursework for **CSC248 — Data Structures** at UiTM, written in the author's third semester
(around 2021) in plain Java with no build tool. The collection shows three threads of the
course:

1. **Abstract data types** (`adts/`) — a hand-rolled generic singly linked list
   (`Object` payload), with `Stack` and `Queue` implemented by extending it.
2. **Sorting and searching** (`sorting-searching/`) — the same algorithm set (bubble sort,
   insertion sort, binary search) re-implemented five times: over ints, Strings, and
   `Student` objects, ascending then descending, plus a menu-style `ProgramApp`.
3. **CPU scheduling** (`scheduling-prototypes/`) — four progressive prototypes
   (Proto5 → Proto8) of a non-preemptive shortest-job-first scheduler over
   `ArrayList<Job>`, each adding a feature (queue sorting, waiting-time tracking, average
   statistics, cleaned output).

`misc/` holds one tiny for-each language demo.

## Import history and file mapping

Imported 2026-08 from the archived folder
`...\UITM\SEM 5\Preparation\Projek\CSC 248( JAVA - data structure)\Other\JAVA-Textpad (sem3)\`.
The archive was flat; the repo groups files by topic:

| Archive file(s) | Repo location |
| --- | --- |
| `LinkedList.java`, `Node.java`, `Stack.java`, `Queue.java` | `adts/` |
| `ProgramApp.java`, `SortingAndSearchingV1–V5.java`, `Student.java` | `sorting-searching/` |
| `Proto5–Proto8.java`, `Job.java` | `scheduling-prototypes/` |
| `ForEachExample1.java` | `misc/` |
| `*.class`, shortcut `.lnk` files | not imported (build artifacts / links) |

Sanitization: the student matric number was removed from the `ProgramApp.java` header
(names of the author, group, and lecturer were kept). No other file carried personal data.

## Relationship to java-linked-list-sorting

The finished product of the scheduling thread — a linked-list-based SJF simulator
(`mainApp` + Job-typed `LinkedList`) — is published separately as
[`java-linked-list-sorting`](https://github.com/dxiiren/java-linked-list-sorting). This repo
keeps the distinct precursors and ADTs:

- `scheduling-prototypes/Job.java` is an **earlier variant** of that repo's `Job` (no
  `burstTime`); kept because Proto5–8 compile against it.
- `adts/Node.java` and `adts/LinkedList.java` are the **generic `Object`-payload** versions
  (that repo's are Job-typed with sorting); the generic list also backs `Stack` and `Queue`.
- The Proto5–8 prototypes are `ArrayList`-based and structurally different from `mainApp`.

## Key facts

| Fact | Value |
| --- | --- |
| Language | Java, default package, JDK 11+ (no build tool, no dependencies) |
| Programs with `main` | 11 (`just list`) |
| Compilation model | each top-level folder is a self-contained unit; shared `out\` |
| Input | stdin via `Scanner`; canned inputs in `sample-inputs/` |
| Output | console only — no files written at runtime |

## Related docs

| Doc | Why |
| --- | --- |
| [architecture.md](architecture.md) | How the three program families are structured |
| [../02-setup/getting-started.md](../02-setup/getting-started.md) | First build and run |
| [../05-reference/input-formats.md](../05-reference/input-formats.md) | Per-program stdin contracts |
