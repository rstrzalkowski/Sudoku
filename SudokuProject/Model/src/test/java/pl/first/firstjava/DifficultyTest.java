package pl.first.firstjava;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class DifficultyTest {

    SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());


    @Test
    public void enumTest() {
        board.solveGame();
        Difficulty.Easy.removePieces(board);
        int counter = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board.get(i, j) == 0) {
                    counter++;
                }
            }
        }
        assertTrue(counter == 15);
    }

    @Test
    public void mediumTest() {
        int counter = 0;
        board.solveGame();
        Difficulty.Medium.removePieces(board);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board.get(i, j) == 0) {
                    counter++;
                }
            }
        }
        assertTrue(counter == 30);
    }

    @Test
    public void hardTest() {
        int counter = 0;
        board.solveGame();
        Difficulty.Hard.removePieces(board);
        for (int i = 0; i < 9;i++) {
            for (int j = 0;j < 9;j++) {
                if (board.get(i,j) == 0) {
                    counter++;
                }
            }
        }
        assertTrue(counter == 45);
    }


}