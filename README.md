# Maze Connectivity in Java

A Union Find maze-connectivity project from Data Structures coursework at South Brunswick High School. Load a square maze and check whether two open cells belong to the same connected region.

## Included

| File | Purpose |
| --- | --- |
| `MazeChecker.java` | File loading, node mapping, unions, and reachability queries. |
| `UF.java` / `UF_FastFind.java` | Local dependencies so this project compiles independently. |
| `data/maze/maze10.txt` | Small assignment maze with six reference queries. |
| `data/maze/maze100.txt` | Large assignment maze with four exit queries. |
| `data/maze/maze2.txt` | Diagonal-only example that must be disconnected. |

## Compile and run

Requires **JDK 17 or newer**. Run commands from this repository's root. No external dependencies or sibling repositories are needed.

```sh
javac --release 17 -d build "@sources.txt"
java -cp build MazeChecker
java -cp build MazeChecker data/maze/maze2.txt 0 3
java -cp build MazeChecker data/maze/maze100.txt 49 9919 49 9939 49 9959 49 9979
```

`sources.txt` lists only the working source files in `src/`. Classes retain the default package used in class.

## Notes

Maze files contain exactly N rows of N characters, without a size header. Spaces are open cells and `#` marks walls. Preserve leading and trailing spaces. Coordinates start at zero; node IDs use `row * N + col`.

Movement is horizontal or vertical. Diagonals do not connect. A wall cannot reach itself; an open cell can. Query coordinates and IDs are assumed valid, as in the assignment.

The small-maze queries return `true, false, true, false, false, true`. The large-maze exits at 9919, 9939, 9959, and 9979 return `true, false, true, false`. The diagonal query `0 -> 3` returns `false`.

The loader rejects empty grids, invalid characters, unequal row lengths, and missing or extra rows. The project uses Quick Find and answers connectivity queries; it does not print a route or shortest path.

## Checks

```sh
bash scripts/check.sh
```

Or compile and run the same topic checks manually:

```sh
javac --release 17 -d build "@sources.txt"
javac --release 17 -cp build -d build tests/MazeConnectivityChecks.java
java -cp build MazeConnectivityChecks
```

## Original code

`archive/originals/` preserves the recovered topic-specific originals, including earlier starters where applicable. Those snapshots are excluded from compilation. See [source notes](docs/SOURCE_NOTES.md) for working-copy changes.

Samuel Asefa · Data Structures · 2026–2027
