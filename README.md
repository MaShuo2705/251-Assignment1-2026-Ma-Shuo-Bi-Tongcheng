# 251 Assignment 1 — Text Editor

**Course:** 159.251 Software Design and Construction

## Group Members

| Member | Name | Student ID |
|--------|------|------------|
| A | Bi Tongcheng (毕同程) | 25014402 |
| B | Ma Shuo (马朔) | 25014500 |

## Project Overview

A standard GUI text editor written in **Java (Swing)**, built with **Maven**,
configured through a **YAML** file, and managed on a **private GitHub repository**
with full version control and issue tracking.

---

## How to Run

### Prerequisites
- **JDK 21** (the project compiles with `--release 21`)
- **Maven 3.9+** (or use the `mvn` from your environment)
- No local `.jar` files are bundled — all dependencies are pulled from Maven Central.

### Build & run (Member A's part, the root Maven project)
```bash
# from the repository root
mvn clean package          # compiles and runs the unit tests
mvn exec:java              # launches the text editor GUI
```

When the GUI opens you will see the main window with the menu bar
**File / Search / View / Help**.

### Run the JUnit tests
```bash
mvn test
```
Result: **11 tests, all passing** (open, save and YAML configuration tests).

---

## Folder Layout

```
251-Assignment1-2026-Ma-Shuo-Bi-Tongcheng/
├── pom.xml                                  # Maven build (Member A base)
├── README.md                                # This document
├── src/
│   ├── main/
│   │   ├── java/texteditor/
│   │   │   ├── TextEditorApp.java           # entry point
│   │   │   ├── gui/EditorFrame.java         # main window + menu bar
│   │   │   ├── gui/EditorPane.java          # editing area + search highlight
│   │   │   ├── io/DocumentLoader.java       # shared open interface
│   │   │   ├── io/PlainTextLoader.java      # .txt loader (Member A)
│   │   │   ├── io/DocumentLoaderRegistry.java
│   │   │   ├── io/TextFileWriter.java       # save (Member A)
│   │   │   └── config/EditorConfig*.java    # YAML config module (Member A)
│   │   └── resources/editor-config.yaml     # YAML default configuration
│   └── test/java/texteditor/                # JUnit tests (open/save/config)
├── 159251/Assignment1/                      # Member B's separate draft project
│                                            # (see note below)
└── reports/                                 # (Member B: SpotBugs/PMD/metrics)
```

> **Note on `159251/Assignment1/`:** this is a separate draft created by
> Member B directly on `main`. It is **not** part of Member A's deliverable
> and is kept as-is. See the "Status" section below.

---

## Representative Git Commit IDs

### Member A — Bi Tongcheng (25014402)
| Commit | Description |
|--------|-------------|
| `243b09f` | Main GUI window with File/Search/View/Help menus — New/Open/Save/Exit/Search/SCPC/Time&Date/About/Print |
| `5b7411b` | YAML configuration module (EditorConfig + loader with fallback defaults) |

### Member B — Ma Shuo (25014500)
| Commit | Description |
|--------|-------------|
| `199be60` | Initialize Maven + basic code (draft project) |
| `3b186de` | Add time and date module |

---

## Private Repository

The repository is private: `251-Assignment1-2026-Ma-Shuo-Bi-Tongcheng`
owned by `MaShuo2705`.

- GitHub: https://github.com/MaShuo2705/251-Assignment1-2026-Ma-Shuo-Bi-Tongcheng

---

## Work / Status Summary

### Member A (Bi Tongcheng) — COMPLETE
- Swing main window with **File / Search / View / Help** menu bar
- **New / Open (.txt) / Save (.txt) / Exit / Search / Select-Copy-Paste-Cut /
  Time & Date / About / Print**
- **YAML configuration** (`editor-config.yaml`) read with SnakeYAML and applied
  to the GUI (default font colour, window size, etc.)
- Exception handling and comments on all classes
- JUnit tests for `open()` and `save()` (bonus) — 11 tests passing
- Git workflow: branch `feature/gui-base`, 5 issues created, worked on and
  closed, PR #6 merged into `main`

### Member B (Ma Shuo) — PARTIALLY COMPLETE (pending)
Per the agreed division of labour, Member B was responsible for:
- Reading **ODT / RTF** files and **PDF export** (PDFBox)
- **Syntax highlighting** for `.java` / `.py` / `.js`
- Maven quality plugins (**SpotBugs / PMD / metrics**) → `reports/`
- **GitHub Actions CI** and **Dockerfile** / container
- Writing the **full README**

As of the submission cut-off, Member B's advanced file formats, CI and Docker
have **not yet been completed** and no `feature/advanced-file-devops` branch has
been pushed. The `159251/Assignment1/` draft exists on `main` but is incomplete.
These items remain open.
