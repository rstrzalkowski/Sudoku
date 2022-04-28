package pl.first.firstjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SudokuStructureTest {
    private final SudokuSolver solver = new BacktrackingSudokuSolver();
    private final SudokuBoard board = new SudokuBoard(solver);



    @Test
    public void verifyValidTest() {
        SudokuRow row = new SudokuRow(new SudokuField[]{
                new SudokuField(1),
                new SudokuField(2),
                new SudokuField(3),
                new SudokuField(4),
                new SudokuField(5),
                new SudokuField(6),
                new SudokuField(7),
                new SudokuField(8),
                new SudokuField(9)});
        assertTrue(row.verify());
    }

    @Test
    public void verifyInvalidTest() {
        SudokuRow row = new SudokuRow(new SudokuField[]{
                new SudokuField(1),
                new SudokuField(2),
                new SudokuField(3),
                new SudokuField(4),
                new SudokuField(5),
                new SudokuField(6),
                new SudokuField(7),
                new SudokuField(9),
                new SudokuField(9)});
        assertFalse(row.verify());

    }

    @Test
    void testEquals() {
        board.solveGame();
        SudokuRow row = board.getRow(0);
        assertEquals(row, row);
        assertNotEquals(board.getRow(0), board.getRow(5));
        assertNotEquals(board.getRow(0), board.getColumn(0));

        assertEquals(board.getColumn(0), board.getColumn(0));
        assertNotEquals(board.getColumn(0), board.getColumn(5));

        assertEquals(board.getBox(2, 2), board.getBox(0, 0));
        assertNotEquals(board.getBox(0, 0), board.getBox(5, 0));
        assertNotEquals(board.getBox(0, 0),null);
    }

    @Test
    void testHashCode() {
        board.solveGame();
        assertEquals(board.getBox(0, 0).hashCode(), board.getBox(2, 0).hashCode());
        assertNotEquals(board.getRow(0).hashCode(), board.getBox(0, 0).hashCode());
    }

    @Test
    void testEqualsAndHashCodeContract() {
        SudokuRow row0 = board.getRow(0);
        SudokuRow row1 = board.getRow(1);
        SudokuColumn col = board.getColumn(0);

        assertTrue(row0.equals(row1) && (row0.hashCode() == row1.hashCode()));
        assertFalse((row0.hashCode() == col.hashCode()) && row0.equals(col));
        board.solveGame();
        assertEquals((row0.hashCode() == col.hashCode()), row0.equals(col));

    }

    @Test
    void testToString() {
        assertNotNull(board.getRow(0).toString());
        assertNotNull(board.getColumn(0).toString());
        assertNotNull(board.getBox(0,0).toString());
    }

    @Test
    void testClone() throws CloneNotSupportedException {
        board.solveGame();
        SudokuRow row = board.getRow(0);
        SudokuColumn col = board.getColumn(2);
        SudokuBox box = board.getBox(4,5);

        SudokuRow rowCloned = row.clone();
        SudokuColumn colCloned = col.clone();
        SudokuBox boxCloned = box.clone();

        assertNotSame(row,rowCloned);
        assertNotSame(col,colCloned);
        assertNotSame(box,boxCloned);



    }
}
