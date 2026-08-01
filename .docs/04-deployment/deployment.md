# Deployment

> **TL;DR** — There is none. These are run-to-completion console programs executed locally
> with `just run <name>`; no CI/CD, no server, no artifacts published anywhere.

## Honest status

| Aspect | Status |
| --- | --- |
| CI/CD | None — no workflows, no pipelines |
| Hosting / server | None — programs run to completion in a terminal |
| Build artifacts | `out\*.class`, local only, git-ignored |
| Release process | None — `main` on GitHub is the distribution |

## What "shipping" means here

Pushing to `main` on `github.com/dxiiren/java-data-structures`. Anyone cloning the repo
reproduces the runtime with `pwsh ./setup.ps1` + `just build-all` — that pair is the whole
deployment story.

## If CI were ever added

The natural gate is exactly what `/lint-check` runs locally: per-folder
`javac -Xlint:all` (one accepted `[serial]` baseline warning) plus `just build-all` and two
representative `just run` programs. Keep that parity if a workflow is ever introduced.

## Related docs

| Doc | Why |
| --- | --- |
| [../02-setup/getting-started.md](../02-setup/getting-started.md) | The local "deployment" |
| [../05-reference/commands.md](../05-reference/commands.md) | Build/run recipes |
