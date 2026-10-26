import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class MazeConnectivityChecks {
    private static int checks;

    private static void check(boolean condition, String message) {
        checks++;
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private static void equal(Object expected, Object actual, String message) {
        check(Objects.equals(expected, actual), message + ": expected " + expected + ", got " + actual);
    }

    private static String normalized(String text) {
        return text.replace("\r\n", "\n");
    }

    private static String run(String className, String keyboard, String... args) throws Exception {
        ArrayList<String> command = new ArrayList<>();
        command.add(Path.of(System.getProperty("java.home"), "bin", "java").toString());
        command.add("-cp");
        command.add(System.getProperty("java.class.path"));
        command.add(className);
        command.addAll(Arrays.asList(args));
        Process process = new ProcessBuilder(command).start();
        process.getOutputStream().write(keyboard.getBytes(StandardCharsets.UTF_8));
        process.getOutputStream().close();
        String output = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        String errors = new String(process.getErrorStream().readAllBytes(), StandardCharsets.UTF_8);
        equal(0, process.waitFor(), className + " process exit (" + errors + ")");
        return normalized(output);
    }

    private static void checkMazes(Path temporary) throws IOException {
        MazeChecker small = new MazeChecker("data/maze/maze10.txt");
        equal(37, small.nodeNumber(3, 7), "Maze node mapping");
        int[][] pairs = {{1, 98}, {1, 17}, {17, 37}, {1, 0}, {0, 0}, {1, 1}};
        boolean[] expected = {true, false, true, false, false, true};
        for (int i = 0; i < pairs.length; i++) {
            equal(expected[i], small.canReach(pairs[i][0], pairs[i][1]), "maze10 query " + i);
        }
        check(small.canReach(0, 1, 9, 8), "Maze coordinate overload");
        MazeChecker large = new MazeChecker("data/maze/maze100.txt");
        int[] exits = {9919, 9939, 9959, 9979};
        for (int i = 0; i < exits.length; i++) {
            equal(i % 2 == 0, large.canReach(49, exits[i]), "maze100 exit " + exits[i]);
        }
        MazeChecker diagonal = new MazeChecker("data/maze/maze2.txt");
        check(!diagonal.canReach(0, 3), "Diagonal movement is forbidden");
        check(diagonal.canReach(0, 0), "Open cell reaches itself");
        check(!diagonal.canReach(1, 1), "Wall does not reach itself");
        for (String invalid : new String[]{"", "##\n#\n", "##\n", "##\n##\n##\n", "##\n#X\n"}) {
            Path file = temporary.resolve("bad-maze.txt");
            Files.writeString(file, invalid);
            boolean rejected = false;
            try {
                new MazeChecker(file.toString());
            } catch (IOException e) {
                rejected = true;
            }
            check(rejected, "Reject malformed maze");
        }
        System.out.println("PASS: maze10, maze100, diagonal movement, walls, and malformed inputs");
    }

    public static void main(String[] args) throws Exception {
        Path temporary = Files.createTempDirectory("topic-checks-");
        try {
            checkMazes(temporary);
            System.out.println("All " + checks + " checks passed.");
        } finally {
            try (java.util.stream.Stream<Path> paths = Files.walk(temporary)) {
                for (Path path : paths.sorted(java.util.Comparator.reverseOrder()).toArray(Path[]::new)) {
                    Files.deleteIfExists(path);
                }
            }
        }
    }
}
