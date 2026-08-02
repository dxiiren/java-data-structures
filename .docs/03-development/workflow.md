# Development workflow

> **TL;DR** — Edit a program, `just run <name>`, keep its `sample-inputs\<name>.txt` in sync
> with any Scanner change, branch + Conventional Commits, no auto-commit. This is preserved
> coursework — fix behavior only when asked; don't modernize for style.

## The loop

1. Edit the `.java` file(s) in one topic folder.
2. `just run <name>` — recompiles that program (and its same-folder dependencies) and runs
   it against its sample input.
3. If you changed any `Scanner` read (added, removed, reordered): update
   `sample-inputs\<name>.txt` to match **in the same change**, and update
   [`../05-reference/input-formats.md`](../05-reference/input-formats.md).
4. Before a PR: `just build-all` (all four folders must PASS), `just test` (7/7 golden
   diffs must PASS — if you intentionally changed a covered program's output, regenerate
   its `tests\expected\<name>.txt` from a verified run; the failing run's stdout is at
   `out\<name>.actual.txt`), and `/lint-check`.

## Ground rules

- **Preservation first.** This is university coursework kept as-written. Malay comments,
  2021-era naming, and decorative output stay. Fix crashes or behavior only when that is
  the task.
- **One `out\`, unique class names.** All folders share `out\` and the default package —
  never introduce a class name that exists in another folder.
- **No new tooling uninvited.** No Maven/Gradle, no Checkstyle, no test framework unless
  the developer asks.
- **Skills.** `/commit` (scopes: `adts`, `sorting`, `scheduling`, `misc`, `input`,
  `tooling`, `docs`, `skills`), `/pre-pr-review` before a PR, `/lint-check` for the
  warning + smoke gate.

## Branching and commits

- Work on feature branches; `main` is the published state.
- Conventional Commits, e.g. `fix(scheduling): guard Proto8 against an empty ready queue`.
- Author email `mohdakmal875@gmail.com` (already set repo-locally). No attribution footers.
- Never commit `out\`, `.mcp.json`, or `.claude/settings.local.json` (all git-ignored).

## Adding a new program

1. Put `NewProgram.java` in the topic folder it belongs to (or a new folder — then add the
   folder to the `build-all` recipe list in `justfile`).
2. If it reads stdin, commit a matching `sample-inputs\NewProgram.txt`.
3. Add a catalog row to `README.md` and an input contract to
   `../05-reference/input-formats.md`.
4. `just run NewProgram` must exit 0 before you PR.

## Related docs

| Doc | Why |
| --- | --- |
| [../05-reference/commands.md](../05-reference/commands.md) | Recipe reference |
| [../05-reference/input-formats.md](../05-reference/input-formats.md) | Stdin contracts you must keep in sync |
| [../01-overview/architecture.md](../01-overview/architecture.md) | Why the folders compile the way they do |
