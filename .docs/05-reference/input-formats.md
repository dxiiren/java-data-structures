# Input formats (stdin contracts)

> **TL;DR** — Every value is read with `Scanner.nextLine()` (one value per line). The
> committed `sample-inputs\<name>.txt` files are the executable spec — keep them in sync
> with any Scanner change. Proto inputs must start at arrival time 1 and leave no idle gap.

All numeric reads are `Integer.parseInt(sc.nextLine())` — a blank or non-numeric line
crashes with `NumberFormatException`.

## sorting-searching/

| Program | Reads, in order | Redirectable? |
| --- | --- | --- |
| `ProgramApp` | 10 integers · 1 integer to search | **interactive only** |
| `SortingAndSearchingV1` | 5 integers (bubble) · 5 integers (insertion) · 1 integer to search | **interactive only** |
| `SortingAndSearchingV2` | 5 names (bubble) · 5 names (insertion) · 1 name to search | **interactive only** |
| `SortingAndSearchingV3` | 5 × (name, number) · 1 number to search | yes (`sample-inputs\` committed) |
| `SortingAndSearchingV4` | 5 integers · 5 names · 1 integer to search · 1 name to search | **interactive only** |
| `SortingAndSearchingV5` | 5 × (name, number) · 1 number to search | yes (`sample-inputs\` committed) |

The search in V1/V2/V4 runs over the **first** array entered (already sorted by then);
V3/V5 search the students' numbers.

**Why four programs are interactive only:** `ProgramApp`, V1, V2, and V4 construct a
**second** `new Scanner(System.in)` inside their search method(s). With redirected stdin
the first Scanner buffers the whole (small) stream on its first read, so the second Scanner
finds the stream at EOF and dies with `NoSuchElementException: No line found` at the search
prompt. At a real keyboard both Scanners work, so these four have no `sample-inputs\*.txt` —
`just run <name>` runs them interactively (coursework source preserved as-is). Example
sequences that work at the keyboard:

```
ProgramApp:  34 7 23 32 5 62 78 1 99 15   then search: 23
V1:          5 3 8 1 9   7 2 6 4 10       then search: 8
V2:          delta alpha charlie echo bravo   zulu yankee xray whiskey victor   search: echo
V4:          5 9 1 7 3   delta bravo echo alpha charlie   search: 7   then: delta
```

(one value per line at the actual prompts)

## scheduling-prototypes/ (Proto5–Proto8, shared contract)

Per job, three lines, repeated:

```
<cpu time>          integer >= 1
<arrival time>      integer >= 1
<yes|no>            yes = read another job, anything else = stop
```

**Liveness rules** (violating either crashes the run):

1. The first-entered job must have arrival time 1 (Proto6–8 seed the queue from
   `arrivalTime == clock` with clock starting at 1; Proto5 force-queues the first job, so
   this rule keeps behavior consistent across all four).
2. Arrivals must keep the CPU busy — if the ready queue is empty at the moment a job
   completes, `queue.get(0)` throws `IndexOutOfBoundsException`.

The committed samples: Proto5/Proto6 use jobs A(cpu 3, arr 1), B(cpu 2, arr 2);
Proto7/Proto8 use A(cpu 4, arr 1), B(cpu 1, arr 2), C(cpu 2, arr 3) — B jumps ahead of C in
the queue, demonstrating the SJF sort.

## misc/

`ForEachExample1` reads nothing — `just run ForEachExample1` notes the missing sample file
and runs it directly.

## adts/

No runnable program (library classes only) — nothing reads stdin.

## Related docs

| Doc | Why |
| --- | --- |
| [commands.md](commands.md) | How samples are piped (`cmd /c` redirect, not a PowerShell pipe) |
| [../01-overview/architecture.md](../01-overview/architecture.md) | Why the Proto liveness assumption exists |
| [../06-troubleshooting/common-issues.md](../06-troubleshooting/common-issues.md) | The crashes bad input causes |
