package day15;

import java.util.List;
import java.util.Stack;
import java.util.Arrays;

public class WordSearch79 {
    public boolean exist(char[][] board, String word) {
        final boolean[][] visited = new boolean[board.length][board[0].length];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (search(board, visited, i, j, 0, word)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean search(char[][] board, boolean[][] visited, int col, int row, int idx, String answer) {
        if (col < 0 || col >= board.length || row < 0 || row >= board[0].length) {
            return false;
        }
        if (visited[col][row]) {
            return false;
        }
        if (answer.charAt(idx) != board[col][row]) {
            return false;
        }
        visited[col][row] = true;
        idx++;

        if (idx == answer.length()) {
            return true;
        }

        final boolean found = search(board, visited, col - 1, row, idx, answer) || search(board, visited, col + 1, row, idx, answer) || search(board, visited, col, row - 1, idx, answer) || search(board, visited, col, row + 1, idx, answer);

        visited[col][row] = false;
        return found;
    }

    private static boolean isPassed(boolean actual, boolean expected) {
        return actual == expected;
    }

    static void main(String[] args) {
        WordSearch79 solver = new WordSearch79();

        char[][] board1 = {
                {'A', 'B', 'C', 'E'},
                {'S', 'F', 'C', 'S'},
                {'A', 'D', 'E', 'E'}
        };
        char[][] board2 = {
                {'A', 'B', 'C', 'E'},
                {'S', 'F', 'C', 'S'},
                {'A', 'D', 'E', 'E'}
        };
        char[][] board3 = {
                {'A', 'B', 'C', 'E'},
                {'S', 'F', 'C', 'S'},
                {'A', 'D', 'E', 'E'}
        };
        char[][] board4 = {{'A'}};
        char[][] board5 = {{'A'}};
        char[][] board6 = {
                {'C', 'A', 'A'},
                {'A', 'A', 'A'},
                {'B', 'C', 'D'}
        };
        char[][] board7 = {
                {'A', 'B'},
                {'C', 'D'}
        };

        Object[][] testCases = {
                {board1, "ABCCED", true},
                {board2, "SEE", true},
                {board3, "ABCB", false},
                {board4, "A", true},
                {board5, "B", false},
                {board6, "AAB", true},
                {board7, "ACDB", true},
                {board7, "ABDC", true},
        };

        for (int i = 0; i < testCases.length; i++) {
            char[][] board = (char[][]) testCases[i][0];
            String word = (String) testCases[i][1];
            boolean expected = (boolean) testCases[i][2];
            boolean actual = solver.exist(board, word);
            System.out.println("Test " + (i + 1) + ": word=\"" + word
                    + "\", actual=" + actual + ", expected=" + expected
                    + ", isPassed=" + isPassed(actual, expected));
        }
    }
}
