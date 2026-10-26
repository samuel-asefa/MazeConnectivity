import java.io.*;

public class MazeChecker {
    private char[][] maze;
    private UF uf;
    private int n;

    public MazeChecker(String fileName) throws IOException {
        loadMaze(fileName);
        uf = new UF_FastFind(n * n);  // You can use either implementaion of UF
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
		// TODO: Convert row/col coordinates to a UF Node number
        return 0;  // placeholder
    }

    private void connectOpenCells() {
       // TODO:  Check connections for each open corridor space
       // Use nested loops to visit every cell.
       // If the cell is a wall '#' no need to check for connection.
       // If open ' ', check left, right, up and down (but not diagonal)
       // If current cell and tested cell are both open, create a union.
       // NOTE: You don't need to duplicate union operations so if you already
       // did union(32,33), there is no need to do union(33,32) [no harm either, but innefficient]
    }

    // Coordinates are assumed to be within the maze.
    public boolean canReach(int startRow, int startCol, int endRow, int endCol) {

        return false; //placeholder
    }

    // Node numbers are assumed to be from 0 through n * n - 1.
    public boolean canReach(int start, int end) {

        return false; //placeholder
    }

    public void display() {
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++)
                System.out.print(maze[row][col]);
            System.out.println();
        }
    }

    public static void main(String[] args) {
        String fileName = "maze10.txt";

        try {
            MazeChecker maze = new MazeChecker(fileName);
            maze.display();
            System.out.println();
            // CHECK THESE FOR PROVIDED maze10.txt
            System.out.println("1 -> 98: " + maze.canReach(1, 98) + " (expected true)");
            System.out.println("1 -> 17: " + maze.canReach(1, 17) + " (expected false)");
            System.out.println("17 -> 37: " + maze.canReach(17, 37) + " (expected true)");
            System.out.println("1 -> 0: " + maze.canReach(1, 0) + " (expected false)");
            System.out.println("0 -> 0: " + maze.canReach(0, 0) + " (expected false)");
            System.out.println("1 -> 1: " + maze.canReach(1, 1) + " (expected true)");
            /* When satisfied, check the following with maze100.txt
			System.out.println("1 -> 1: " + maze.canReach(49, 9_919)); // row 100 col 19
			System.out.println("1 -> 1: " + maze.canReach(49, 9_939)); // row 100 col 39
			System.out.println("1 -> 1: " + maze.canReach(49, 9_959)); // row 100 col 59
            System.out.println("1 -> 1: " + maze.canReach(49, 9_979)); // row 100 col 79

            */


        } catch (IOException e) {
            System.out.println("Could not load maze: " + e.getMessage());
        }
    }
}
