# Java Data Structures

A collection of 17 plain-Java console programs from **CSC248 — Data Structures (UiTM,
semester 3, ~2021)** coursework: hand-rolled linked list / stack / queue ADTs, five
progressive sorting-and-searching programs, and four progressively refined
shortest-job-first CPU-scheduling prototypes. Preserved as written for the course.

> **New developer? Start with [`.docs/tldr.md`](.docs/tldr.md)** — every doc summarised on one
> page. The full guide lives in [`.docs/`](.docs/README.md).

## Program catalog

| Program | Folder | What it does |
| --- | --- | --- |
| `LinkedList` (+ `Node`) | `adts/` | Generic singly linked list ADT: insert front/back/after, remove front/back, keyed delete, traversal accessors, `EmptyListException`. No `main` — it is the base class of the two ADTs below. |
| `Stack` | `adts/` | Stack ADT (`push`/`pop`/`peek`) implemented by extending `LinkedList`. |
| `Queue` | `adts/` | Queue ADT (`enqueue`/`dequeue`/`getFront`/`getEnd`) implemented by extending `LinkedList`. |
| `ProgramApp` | `sorting-searching/` | Menu-style program: read 10 integers, bubble sort, then binary search for a value. |
| `SortingAndSearchingV1` | `sorting-searching/` | Bubble + insertion sort and binary search over **int** arrays, ascending. |
| `SortingAndSearchingV2` | `sorting-searching/` | Same over **String** arrays (case-insensitive), ascending. |
| `SortingAndSearchingV3` | `sorting-searching/` | Same over an array of **`Student` objects** (numbers and names sorted independently), ascending. |
| `SortingAndSearchingV4` | `sorting-searching/` | Int + String arrays, **descending**, with two binary-search variants. |
| `SortingAndSearchingV5` | `sorting-searching/` | `Student` objects, **descending**. |
| `Student` | `sorting-searching/` | Data class (name, number) used by V3/V5. No `main`. |
| `Proto5` | `scheduling-prototypes/` | First CPU-scheduling prototype: `ArrayList<Job>` clock-tick trace, FCFS order, no sorting. |
| `Proto6` | `scheduling-prototypes/` | Adds waiting-time tracking and insertion-sorting the ready queue by CPU time (SJF). |
| `Proto7` | `scheduling-prototypes/` | Adds per-job execute/wait bookkeeping and average executing/waiting times. |
| `Proto8` | `scheduling-prototypes/` | Final prototype: Proto7 with cleaned-up output (average turn-around time label, debug prints removed). |
| `Job` | `scheduling-prototypes/` | Data class (name, cpu time, arrival time, wait) used by Proto5–8. No `main`. |
| `ForEachExample1` | `misc/` | Tiny for-each loop demo. |

**Cross-reference:** the finished scheduler these prototypes led to — a linked-list-based
SJF simulator (`mainApp`) — is published separately as
[`java-linked-list-sorting`](https://github.com/dxiiren/java-linked-list-sorting).
`Job.java` and `Node.java` here are earlier variants of that repo's same-named classes
(no `burstTime` field; generic `Object` payload) and are kept because Proto5–8 and the
ADTs compile against them.

## Prerequisites

| Tool | Version | Installed by |
| --- | --- | --- |
| PowerShell + winget | Windows 10/11 stock | — (the only true prerequisites) |
| Temurin JDK | 11+ (21 installed if missing) | `setup.ps1` |
| Git | any recent | `setup.ps1` |
| Node.js LTS | for the Claude CLI | `setup.ps1` |
| uv + Python | latest | `setup.ps1` |
| just | any recent | `setup.ps1` |
| Claude Code CLI | latest | `setup.ps1` (optional, for AI-assisted dev) |

## Quick start

```powershell
# 1. One-time machine setup (idempotent — safe to re-run)
pwsh ./setup.ps1

# 2. Close and reopen PowerShell so PATH updates land
just list          # catalog of runnable programs
just build-all     # compile all four folders into out\
just run Proto8    # build + run one program against sample-inputs\Proto8.txt
```

`just run <name>` compiles one program and runs it with `sample-inputs\<name>.txt` piped as
stdin (programs without a sample file run directly). Four programs — `ProgramApp` and
`SortingAndSearchingV1/V2/V4` — are **interactive only** (they open a second `Scanner`,
which breaks redirected stdin), so `just run` on them waits for typed input. These are
run-to-completion CLIs — there is no server to stop.

## Commands

Run `just` with no arguments to list every recipe. The ones you'll use daily:

| Command | What it does |
| --- | --- |
| `just list` | List every runnable program (class with `main`), one per line |
| `just build <name>` | Compile one program (plus same-folder dependencies) into `out\` |
| `just build-all` | Compile every folder; fail on first error; PASS/FAIL summary |
| `just run <name>` | Build + run one program with `sample-inputs\<name>.txt` as stdin |
| `just run-interactive <name>` | Build + run one program with your own typed input |
| `just clean` | Remove compiled classes (`out\`) |
| `just claudex` | Launch Claude Code (Sonnet, all permissions) |

## Troubleshooting

### `just run Proto5` (or any Proto) dies with `IndexOutOfBoundsException`

The prototypes assume the first job arrives at time 1 and that arrivals keep the CPU busy.
An input whose ready queue is empty when a job completes crashes at `queue.get(0)`. Keep
custom inputs gap-free (see `.docs/05-reference/`).

### A program crashes with `NumberFormatException` on the very first prompt

You piped input with PowerShell (`Get-Content file | java ...`) — the 5.1 pipe injects a
UTF-8 BOM into the first line. Use `just run <name>`, which redirects via `cmd /c` instead.

### `NoSuchElementException: No line found` at a search prompt under redirected input

`ProgramApp` and `SortingAndSearchingV1/V2/V4` construct a second `Scanner(System.in)`
inside their search methods; with redirected stdin the first Scanner buffers the whole
stream, so the second one hits EOF. These four are interactive only — run them with
`just run-interactive <name>` (their sample files are intentionally absent; the coursework
source is preserved as-is).

### `javac` prints a `[serial]` warning for `EmptyListException`

Known baseline (one warning, `adts\LinkedList.java`): the nested exception class has no
`serialVersionUID`. Accepted as-is — preserved coursework. Anything beyond that one warning
is new.

### A program just sits there doing nothing

It is waiting for stdin. Either its `sample-inputs\<name>.txt` is missing (check `just run`'s
notice) or you ran `run-interactive` — type the values in the prompt order.

More in [`.docs/06-troubleshooting/common-issues.md`](.docs/06-troubleshooting/common-issues.md).

## Project layout

```
java-data-structures/
  adts/                    # generic linked list ADT family (no main): LinkedList, Node, Stack, Queue
  sorting-searching/       # ProgramApp + SortingAndSearchingV1–V5 + Student (data class)
  scheduling-prototypes/   # Proto5–Proto8 SJF scheduler prototypes + Job (data class)
  misc/                    # ForEachExample1 (tiny for-each demo)
  sample-inputs/           # <ProgramName>.txt canned stdin, one per stdin-reading program
  out/                     # compiled classes (git-ignored)
  .docs/                   # numbered documentation set
  .claude/                 # skills, hooks, settings
  justfile, setup.ps1
```
