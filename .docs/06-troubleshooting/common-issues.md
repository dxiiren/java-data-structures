# Common issues

> **TL;DR** — Every symptom below was hit (or deliberately reproduced) during the initial
> import verification. Check here before debugging from scratch.

## `NoSuchElementException: No line found` at the search prompt (redirected input)

**Symptom** (seen live during import verification on `SortingAndSearchingV1`):

```
Enter a number that you want to search : Exception in thread "main" java.util.NoSuchElementException: No line found
        at java.base/java.util.Scanner.nextLine(Scanner.java:1651)
        at SortingAndSearchingV1.binarySearch(SortingAndSearchingV1.java:106)
```

**Cause (historical)** — `ProgramApp`, `SortingAndSearchingV1`, `V2`, and `V4` used to
construct a **second** `new Scanner(System.in)` inside their search method(s). With
redirected stdin the first Scanner buffers the entire (small) stream on its first read;
the second Scanner then found the underlying stream at EOF. At a real keyboard both
Scanners read fine, which is why the coursework never noticed.

**Resolution** — **fixed in 2026**: the search methods now take main's Scanner as a
parameter, all four have committed `sample-inputs\*.txt` + goldens, and they run under
`just run`/`just test`. If you see this error today, a sample-input file has fewer lines
than the program reads — never reintroduce a second `Scanner(System.in)`.

## Proto program dies with `IndexOutOfBoundsException: Index 0 out of bounds for length 0`

**Cause** — the ready queue was empty at the moment a job completed (`queue.get(0)`).
The prototypes assume the first job arrives at time 1 and arrivals keep the CPU busy.

**Resolution** — keep custom inputs gap-free; see the liveness rules in
[`../05-reference/input-formats.md`](../05-reference/input-formats.md). The committed
Proto samples satisfy them.

## `NumberFormatException` on the very first prompt

**Cause** — input piped with PowerShell 5.1 (`Get-Content file | java ...`), which injects
a UTF-8 BOM into the first stdin line; the first read is `Integer.parseInt(...)`.

**Resolution** — use `just run <name>`; its `cmd /c "java ... < file"` redirection passes
bytes through untouched. Never "simplify" the recipe into a pipe.

## `javac` prints `[serial] serializable class EmptyListException has no definition of serialVersionUID`

**Cause** — `adts\LinkedList.java:166` nests an exception class without a
`serialVersionUID`. Present since the coursework was written.

**Resolution** — accepted baseline; `/lint-check` treats exactly this one warning as PASS.
Anything beyond it is a regression.

## `just run <name>` says "No source file ... in this repo"

**Cause** — the name is case-sensitive-ish and must match the file name exactly
(e.g. `Proto8`, not `proto8`; `SortingAndSearchingV1`, not `V1`).

**Resolution** — `just list` prints the exact names.

## A change compiles but the wrong class seems to run

**Cause** — all four folders share `out\` and the default package. A class name duplicated
across folders silently overwrites the other's `.class` on compile.

**Resolution** — keep class names unique repo-wide (checked in `/pre-pr-review`).

## Related docs

| Doc | Why |
| --- | --- |
| [../05-reference/input-formats.md](../05-reference/input-formats.md) | The contracts whose violation causes most of the above |
| [../05-reference/commands.md](../05-reference/commands.md) | What each recipe actually executes |
| [../07-faq/faq.md](../07-faq/faq.md) | Design-question follow-ups |
