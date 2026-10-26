import java.io.*;

public class MazeChecker {
    private char[][] maze;
    private UF uf;
    private int n;

    public MazeChecker(String fileName) throws IOException {
        loadMaze(fileName);
        uf = new UF_FastFind(n * n);
        connectOpenCells();
    }

    private void loadMaze(String fileName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
            if (line == null || line.length() == 0)
                throw new IOException("Maze must not be empty.");

            n = line.length();
            maze = new char[n][n];

            for (int row = 0; row < n; row++) {
                if (line == null || line.length() != n)
                    throw new IOException("Maze must have " + n + " rows of " + n + " chars.");

                for (int col = 0; col < n; col++) {
                    char cell = line.charAt(col);
                    if (cell != ' ' && cell != '#')
                        throw new IOException("Use only spaces ' ' and # characters.");
                    maze[row][col] = cell;
                }
                line = br.readLine();
            }
            if (line != null)
                throw new IOException("Too many rows in maze.");
        }
    }

    public int nodeNumber(int row, int col) {
        return row * n + col;
    }

    private void connectOpenCells() {
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (maze[row][col] == '#') {
                    continue;
                }
                int current = nodeNumber(row, col);
                if (col + 1 < n && maze[row][col + 1] == ' ') {
                    uf.union(current, nodeNumber(row, col + 1));
                }
                if (row + 1 < n && maze[row + 1][col] == ' ') {
                    uf.union(current, nodeNumber(row + 1, col));
                }
            }
        }
    }

    public boolean canReach(int startRow, int startCol, int endRow, int endCol) {
        if (maze[startRow][startCol] == '#' ||
            maze[endRow][endCol] == '#') {
            return false;
        }
        return uf.connected(nodeNumber(startRow, startCol), nodeNumber(endRow, endCol));
    }

    public boolean canReach(int start, int end) {
        int startRow = start / n;
        int startCol = start % n;
        int endRow = end / n;
        int endCol = end % n;
        return canReach(startRow, startCol, endRow, endCol);
    }

    public void display() {
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                System.out.print(maze[row][col]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        if (args.length > 1 && args.length % 2 == 0) {
            System.err.println("Usage: MazeChecker [file [start end ...]]");
            return;
        }
        String fileName = args.length > 0 ? args[0] : "data/maze/maze10.txt";
        try {
            MazeChecker checker = new MazeChecker(fileName);
            if (checker.n <= 10) {
                checker.display();
            }
            if (args.length > 1) {
                for (int i = 1; i < args.length; i += 2) {
                    int start = Integer.parseInt(args[i]);
                    int end = Integer.parseInt(args[i + 1]);
                    System.out.println(start + " -> " + end + ": " + checker.canReach(start, end));
                }
            } else if (checker.n == 10) {
                int[][] pairs = {{1, 98}, {1, 17}, {17, 37}, {1, 0}, {0, 0}, {1, 1}};
                for (int[] pair : pairs) {
                    System.out.println(pair[0] + " -> " + pair[1] + ": "
                            + checker.canReach(pair[0], pair[1]));
                }
            } else {
                System.out.println("Maze loaded. Add start and end node numbers to test a connection.");
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Could not run maze: " + e.getMessage());
        }
    }
}
