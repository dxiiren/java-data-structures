# Architecture

> **TL;DR** — Three independent program families in four folders, all default-package Java.
> Each folder compiles on its own (same-folder files are the only dependencies) into one
> shared `out\`. The ADT family is an inheritance chain; the sorting programs are standalone
> mains; the scheduler prototypes share the `Job` data class.

## Compilation model

There is no build tool. Each top-level folder is one compilation unit:

```
javac -d out -sourcepath <folder> <folder>\<Program>.java
```

`-sourcepath` pulls in same-folder dependencies automatically (`Job`, `Student`, `Node`,
`LinkedList`). Everything lands in one shared `out\`, so **class names must stay unique
across folders** — a second `Student` or `Node` anywhere would silently overwrite the
first's `.class`.

## Family 1 — ADTs (`adts/`, no main)

```
Node          (Object data + next pointer)
  ^ used by
LinkedList    (firstNode/lastNode/currNode; insertAtFront/Back, insertAfter, search,
   |           removeFromFront/Back, deleteNode, getFirst/getLast/getNext, display;
   |           nested EmptyListException)
   +-- Stack  (push/pop/peek  -> insertAtFront/removeFromFront/getFirst)
   +-- Queue  (enqueue/dequeue/getFront/getEnd -> insertAtBack/removeFromFront/...)
```

Design notes (coursework-era, preserved):

- Payload type is raw `Object`; callers cast on the way out.
- `search`/`deleteNode` compare payloads with `==`/`!=` — reference identity, correct only
  for interned strings or cached `Integer` values.
- `EmptyListException` prints its message in the constructor and carries none.
- There is deliberately **no driver class** — the folder is a library the course built on.

## Family 2 — Sorting and searching (`sorting-searching/`)

Six standalone mains + one data class. The V1–V5 sequence re-implements the same three
algorithms with a changing element type and direction:

| Version | Element type | Direction |
| --- | --- | --- |
| V1 | `int[]` | ascending |
| V2 | `String[]` (case-insensitive) | ascending |
| V3 | `Student[]` (object ADT) | ascending |
| V4 | `int[]` + `String[]` | descending |
| V5 | `Student[]` | descending |

Quirk worth knowing: V3/V5 sort the students' **numbers and names independently** (bubble
sort swaps only numbers, insertion sort swaps only names), so a `Student`'s name/number
pairing is not preserved — faithful to the original exercise.

`ProgramApp` is the polished single-run variant: 10 integers, bubble sort, binary search.

## Family 3 — Scheduling prototypes (`scheduling-prototypes/`)

Four mains over `ArrayList<Job>`, one data class. Each prototype is the previous one plus a
feature — the diff chain is the point of keeping all four:

| Prototype | Adds |
| --- | --- |
| Proto5 | clock-tick loop, arrival detection, execution trace (FCFS — no sorting) |
| Proto6 | waiting-time tracking, `insertionSort(queue)` by CPU time (makes it SJF) |
| Proto7 | per-job execute/wait arrays, average executing + waiting times |
| Proto8 | output cleanup: "average turn-around time" label, debug prints removed |

Shared input contract: per job, `cpu time`, `arrival time`, then `yes`/`no` to add more.
Shared liveness assumption: the first job arrives at time 1 and arrivals keep the CPU busy;
an idle gap crashes at `queue.get(0)` (see troubleshooting).

## Related docs

| Doc | Why |
| --- | --- |
| [project-overview.md](project-overview.md) | Course context and import mapping |
| [../05-reference/input-formats.md](../05-reference/input-formats.md) | Exact stdin ordering per program |
| [../06-troubleshooting/common-issues.md](../06-troubleshooting/common-issues.md) | The idle-gap crash and other quirks |
