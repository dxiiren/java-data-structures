# java-data-structures — documentation

Documentation for the CSC248 data-structures coursework collection: 17 plain-Java console
programs (ADTs, sorting/searching, CPU-scheduling prototypes) with a `just`-based
build/run harness.

> **New here? Start with [tldr.md](tldr.md)** — every doc summarised in 30 seconds each.

## Who is this for?

| Reader | Start here |
| --- | --- |
| New developer setting up the repo | [02-setup/getting-started.md](02-setup/getting-started.md) |
| Someone asking "what is this?" | [01-overview/project-overview.md](01-overview/project-overview.md) |
| Contributor changing a program | [03-development/workflow.md](03-development/workflow.md) |
| Anyone hitting an error | [06-troubleshooting/common-issues.md](06-troubleshooting/common-issues.md) |
| Looking up a command or input format | [05-reference/](.#05-reference) |

## Recommended reading order

1. [tldr.md](tldr.md)
2. [01-overview/project-overview.md](01-overview/project-overview.md)
3. [02-setup/getting-started.md](02-setup/getting-started.md)
4. [01-overview/architecture.md](01-overview/architecture.md)
5. [05-reference/input-formats.md](05-reference/input-formats.md)
6. [03-development/workflow.md](03-development/workflow.md)

## 01-overview

| Document | What it covers |
| --- | --- |
| [project-overview.md](01-overview/project-overview.md) | Course context, import history/mapping, sanitization, lineage of the finished SJF scheduler |
| [architecture.md](01-overview/architecture.md) | The three program families, the per-folder compilation model, design quirks |

## 02-setup

| Document | What it covers |
| --- | --- |
| [getting-started.md](02-setup/getting-started.md) | setup.ps1, first build/run, verification checklist |

## 03-development

| Document | What it covers |
| --- | --- |
| [workflow.md](03-development/workflow.md) | Day-2 loop, preservation rules, branching, adding a program |

## 04-deployment

| Document | What it covers |
| --- | --- |
| [deployment.md](04-deployment/deployment.md) | Honest status: no CI/CD, runs locally; what shipping means |

## 05-reference

| Document | What it covers |
| --- | --- |
| [commands.md](05-reference/commands.md) | Every just recipe + the cmd /c redirect rationale |
| [input-formats.md](05-reference/input-formats.md) | Per-program stdin contracts, Proto liveness rules |
| [project-layout.md](05-reference/project-layout.md) | Annotated tree + the rules the layout encodes |

## 06-troubleshooting

| Document | What it covers |
| --- | --- |
| [common-issues.md](06-troubleshooting/common-issues.md) | Real symptoms from the import verification (Scanner EOF, idle-gap crash, BOM, serial warning) |

## 07-faq

| Document | What it covers |
| --- | --- |
| [faq.md](07-faq/faq.md) | Why bugs stay, why five V-versions, where the finished scheduler lives |
