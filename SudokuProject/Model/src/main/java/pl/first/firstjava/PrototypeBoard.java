package pl.first.firstjava;

public class PrototypeBoard {
    private SudokuBoard board;

    public PrototypeBoard(SudokuSolver solver) {
        board = new SudokuBoard(solver);
    }

    public SudokuBoard createInstance() throws CloneNotSupportedException {
        return board.clone();
    }
}
