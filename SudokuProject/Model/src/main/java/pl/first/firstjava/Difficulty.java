package pl.first.firstjava;

import java.util.Random;

public enum Difficulty {
    Testing(0),
    Easy(15),
    Medium(30),
    Hard(45);

    private final int value;

    Difficulty(int val) {
        this.value = val;
    }

    public SudokuBoard removePieces(SudokuBoard board) {
        int counter = value;
        while (counter > 0) {
            Random rand = new Random();
            int x = rand.nextInt(9);
            int y = rand.nextInt(9);
            if (board.get(x, y) != 0) {
                board.set(x, y, 0);
                counter--;
            }

        }
        return board;
    }
}