# CLAUDE.md — java-data-structures

> Human-facing developer docs live in [`.docs/`](./.docs/README.md) — start at
> [`.docs/tldr.md`](./.docs/tldr.md). Keep them in sync when changing behavior they document.

## Project: Java Data Structures

A collection of 17 plain-Java console programs from CSC248 (Data Structures, UiTM, ~2021)
coursework: hand-rolled generic linked list / stack / queue ADTs, five progressive
sorting-and-searching programs (bubble sort, insertion sort, binary search over ints,
strings, and objects, ascending and descending), and four progressively refined
shortest-job-first CPU-scheduling prototypes (Proto5–Proto8) that led to a finished
linked-list-based SJF scheduler which formerly lived in a standalone repo (since retired);
these prototypes are its documented ancestors — the lineage is preserved here. As-is.

- **Repo:** GitHub — `github.com/dxiiren/java-data-structures`
- **Runs locally only** — no CI/CD, no deployment target, no server. `just run <name>`
  compiles one program and runs it with `sample-inputs\<name>.txt` piped as stdin.

### Tech Stack Quick Reference

| Layer | Technology | Key details |
| --- | --- | --- |
| Language | Java (JDK 11+; Temurin 21 via setup) | Plain `javac`/`java`, no build tool, no dependencies |
| Entry points | 11 classes with `main()` | `just list` enumerates them; all in the default package |
| ADTs | `LinkedList`, `Node`, `Stack`, `Queue` | Generic `Object`-payload list; `Stack`/`Queue` extend it; no driver program |
| Exercises | `SortingAndSearchingV1–V5`, `ProgramApp` | Bubble/insertion sort + binary search over int/String/`Student` arrays |
| Prototypes | `Proto5–Proto8` + `Job` | `ArrayList<Job>` SJF schedulers, each adding features (sort, wait times, averages) |
| Task runner | `just` | collection recipes: `list`, `build <name>`, `build-all`, `run <name>` |

### Project Structure

```
java-data-structures/
  adts/                    # generic linked list ADT family (no main): LinkedList, Node, Stack, Queue
  sorting-searching/       # ProgramApp + SortingAndSearchingV1–V5 + Student (data class)
  scheduling-prototypes/   # Proto5–Proto8 SJF scheduler prototypes + Job (data class)
  misc/                    # ForEachExample1 (tiny for-each demo)
  sample-inputs/           # <ProgramName>.txt canned stdin, one per stdin-reading program
  tests/                   # golden-output harness — run-tests.ps1 + expected/ (7 goldens);
                           # `just test` must stay 7/7 PASS
  out/                     # compiled classes (git-ignored)
  .docs/                   # numbered documentation set
  .claude/                 # skills, hooks, settings
  justfile, setup.ps1
```

## Git Commits

- **Conventional Commits** (`feat:`, `fix:`, `chore:`, `docs:` ...).
- **NEVER** add `Co-Authored-By` lines or "Generated with Claude Code" / session-link footers to
  **any** outward artifact — commit messages, PR descriptions, or issue comments.
- Commit author email for this repo is `mohdakmal875@gmail.com` (set repo-locally).
- Only stage and commit files relevant to the change. **Never auto-commit** after a fix — the
  developer says "commit" first.

## Local Development

- One-time machine setup: `pwsh ./setup.ps1` (idempotent — installs Git, Node (for the Claude
  CLI), the Temurin JDK, uv/Python, just, the Claude Code CLI). Then `just build-all` and
  `just run <name>`.
- All day-2 commands are `just` recipes — run `just` to list them. Never invent an alternative
  command for something a recipe already covers.
- These are run-to-completion CLIs — there is no server and no `just start`/`just stop`.
- The `run` recipe uses `cmd /c "java ... < sample-inputs\<name>.txt"` on purpose: the Windows
  PowerShell 5.1 pipe (`Get-Content |`) injects a UTF-8 BOM into the first stdin line — several
  programs' first read is `Integer.parseInt`, so a BOM crashes them. Don't "simplify" it back
  to a pipe.
- Each top-level folder is its own compilation unit (same-folder files are the only compile
  dependencies); everything lands in one shared `out\`, so class names must stay unique
  across folders.
- `ProgramApp` and `SortingAndSearchingV1/V2/V4` are **interactive only**: they build a
  second `Scanner(System.in)` inside their search methods, and with redirected stdin the
  first Scanner buffers the whole stream so the second hits EOF
  (`NoSuchElementException`). Their `sample-inputs\*.txt` are intentionally absent — do not
  add them, and do not "fix" the double Scanner (preserved coursework).
- Proto5–8 input contract: per job `cpu time`, `arrival time`, then `yes`/`no` (add more).
  The first job must arrive at time 1 and arrivals must keep the CPU busy — an idle gap
  crashes with `IndexOutOfBoundsException` (`queue.get(0)` on an empty ready queue).
- The ADTs in `adts/` compare `Object` payloads with `==`/`!=` (`search`, `deleteNode`) —
  correct only for interned strings / cached Integer values; preserved coursework behavior.
- `adts/` has no runnable program — `just build LinkedList` compiles the family; there is
  deliberately no driver class.
- The apps write no files at runtime — the only generated artifact is the git-ignored `out\`.
- Job.java and Node.java here are earlier generic/variant versions of the same-named classes
  in the finished SJF scheduler (formerly a standalone repo, since retired) — kept because
  Proto5–8 and the ADTs compile against them.

## Project Skills

Development skills live in `.claude/skills/` — check `.claude/skills/README.md` for the catalog
and **follow the relevant skill before writing code**. Notables: `/commit`, `/create-pr`,
`/pre-pr-review`, `/lint-check`, `/claude-transfer`, `/llm-transfer`, `/define-goal`,
`/setup-mcp`, `/test-all-mcp`, `/audit-skills`.

## MCP Servers

Wired via the committed-stub + git-ignored-secret pattern: `.mcp.json.stub` (committed,
placeholders) → `.mcp.json` (git-ignored, real — seeded by `setup.ps1`). Turnkey: `context7`
(library docs — call `resolve-library-id` then `query-docs` instead of recalling APIs),
`playwright` (drive a real browser). Per-dev: `github` (fill the PAT in `.mcp.json`).
Health check: `/test-all-mcp`. Fall back to native tools silently if a server is unavailable.

## Memory

Lightweight, single-developer, file-based project memory at `.claude/memory/`:

- **`MEMORY.md`** is the index (one line per memory: `- [Title](file.md) — hook`), loaded each
  session.
- Each memory is **one fact in its own `*.md` file** with frontmatter (`name`, `description`,
  `metadata.type` = `reference` | `feedback` | `project`). Read the fact file on demand when its
  index hook is relevant.
- After writing a fact file, add its one-line pointer to `MEMORY.md`. Update rather than
  duplicate; delete a memory that turns out wrong. Don't store what the repo already records.
