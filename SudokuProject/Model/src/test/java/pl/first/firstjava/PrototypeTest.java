package pl.first.firstjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PrototypeTest {

    @Test
    public void createInstance() throws CloneNotSupportedException {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board1 = new SudokuBoard(solver);
        PrototypeBoard repo = new PrototypeBoard(solver);

        SudokuBoard board2 = board1.clone();
        SudokuBoard board3 = repo.createInstance();
        assertEquals(board2,board3);

    }
}