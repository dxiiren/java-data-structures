---
name: pre-pr-review
description: Use when the developer says 'pre-pr review', 'review my branch', 'audit my work', or 'self review' — self-reviews the current branch's diff against a plain-Java CLI checklist (correctness, input handling, cross-folder class clashes, naming, docs sync) before opening a PR, then saves a report to .claude/workspace/reports/pr/.
model: opus
---

# Pre-PR Review (Self-Audit)

Self-review your feature-branch diff **before** opening a PR. This is a collection of small
plain-Java console programs (no build tool, no framework, no tests) — the goal is to catch
correctness, input-handling, and structural problems early, not to restyle a preserved uni
project.

## Trigger

- `"pre-pr review"` / `"self review"`
- `"review my branch"` / `"review my work"` / `"review my code"`
- `"audit my work"` / `"audit my branch"`

## Do NOT flag

- The app's existing decorative output formatting (banners, tab-aligned tables) — that IS the
  product; only flag output changes the branch itself broke.
- Pre-existing patterns the developer copied from the codebase — not this branch's problem.
- Style-only rewrites of untouched code (brace style, field naming from 2021) unless the
  branch touches those lines anyway.

## Step 1 — Branch & base

```bash
git branch --show-current
```

If on `main`: **STOP** — "You're on `main`; switch to your feature branch first."

```bash
git fetch origin main
git diff origin/main...HEAD --name-only
```

If no files changed: **STOP** — "No changes vs `main`."

Scope the review to reviewable source: `*.java`, `sample-inputs/*.txt`, `justfile`, `setup.ps1`.
**Exclude** `.claude/` and generated artifacts. If only excluded files changed: **STOP** —
"No reviewable source changed."

Report: "Branch `{name}` changed {N} source files. Running review."

## Step 2 — Fetch the diff

```bash
git diff origin/main...HEAD -- '*.java' sample-inputs justfile setup.ps1
```

For context-dependent checks (input order, loop bounds), read the **full file**, not just
the hunk.

## Step 3 — Run the checklist

Verify each finding against the actual code before reporting it.

| #   | Check                       | Label      | What to look for                                                                                                                                                            |
| --- | --------------------------- | ---------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| 1   | **Compiles clean**          | issue      | `just build-all` exits 0 (all four folders). Run it — a PR that doesn't compile is dead on arrival.                                                                          |
| 2   | **Runs end-to-end**         | issue      | `just run <name>` exits 0 with no stack trace for every program the branch touched, using its committed `sample-inputs\<name>.txt`.                                          |
| 3   | **Input contract**          | issue      | Any added/reordered `Scanner` read MUST be mirrored in `sample-inputs\<name>.txt` (order + counts) and in `.docs/05-reference/`. Mixed `nextInt()`/`nextLine()` newline bugs. |
| 4   | **Parsing robustness**      | suggestion | `Integer.parseInt` on raw input — does bad input produce a clear message or a raw `NumberFormatException`? Don't demand a rewrite; flag regressions.                          |
| 5   | **Object identity vs equals**| issue     | The list ADTs compare `Object` payloads with `==`/`!=` (`deleteNode`, `search`) — works for interned/int-cached data only. Flag new code that relies on it for other types.   |
| 6   | **Scheduler liveness**      | issue      | Proto5–8 assume arrivals keep the CPU busy — an input with an idle gap makes `queue.get(0)` throw `IndexOutOfBoundsException`. New sample inputs must keep the queue nonempty. |
| 7   | **Cross-folder class clash**| issue      | All classes share the default package and one `out\` tree — a new file must not reuse a class name from another folder (e.g. a second `Student` or `Node`).                   |
| 8   | **No debug leftovers**      | issue      | `System.out.println("here")`-style debugging, commented-out dead blocks, `TODO` without follow-up.                                                                           |
| 9   | **Naming & structure**      | suggestion | New code follows Java conventions (camelCase locals, methods on the class that owns the data) even though old code predates them.                                            |
| 10  | **Docs sync**               | suggestion | Behavior changes reflected in `README.md` / `.docs/` (especially the input-order reference and troubleshooting).                                                             |

## Step 4 — Build & run gate

If any `.java` file or `sample-inputs/*.txt` changed:

```bash
just build-all
just run <each-touched-program>
```

All must exit 0 with no stack trace. Paste the last few output lines of each run as
evidence. A failure is an **issue** (blocking).

## Step 5 — Finding labels & caps

- **issue** (blocking) — fix before opening the PR.
- **suggestion** (non-blocking) — recommended.
- **nitpick** (non-blocking) — minor/optional.

Every finding must carry: the label, the `file:line`, and **WHY** it matters (not just what).
Issues: uncapped. Suggestions + nitpicks: cap at 15 total; note "{X} more non-blocking
findings omitted" if over.

## Step 6 — Present

```
## Pre-PR Review: {branch}
Branch: {branch} -> main   |   Files: {N}
Build/run gate: {pass/fail — exit codes}

### Issues (fix before PR)
1. [path:line] Finding — why it matters

### Suggestions
2. [path:line] Finding

### Nitpicks
3. [path:line] Finding

---
{Total} findings: {issues} issues, {suggestions} suggestions, {nitpicks} nitpicks
```

Zero findings → "No issues found — branch looks clean. Ready to open the PR."

## Step 7 — Save the report

Path: `.claude/workspace/reports/pr/{branch}-{YYYY-MM-DD}.md` (replace `/` in the branch name
with `-`; overwrite on a same-day re-run). Frontmatter then the same body as the terminal
output:

```yaml
---
branch: { branch }
base: main
date: { YYYY-MM-DD }
files_changed: { N }
issues: { count }
suggestions: { count }
nitpicks: { count }
---
```

Confirm: "Report saved to `{path}`".

## Tone

Self-improvement, not a verdict from a lead. "Consider extracting…", not "You must fix…".
Never directive, never judgmental.
