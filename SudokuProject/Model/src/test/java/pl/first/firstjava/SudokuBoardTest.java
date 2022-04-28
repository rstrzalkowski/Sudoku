package pl.first.firstjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


public class SudokuBoardTest {
    SudokuSolver solver = new BacktrackingSudokuSolver();
    SudokuBoard plansza = new SudokuBoard(solver);
    SudokuBoard plansza2 = new SudokuBoard(solver);


    @Test
    void getRowTest() {

        plansza.solveGame();
        SudokuRow row = plansza.getRow(0);
        assertTrue(row.verify());

    }

    @Test
    void getColumnTest() {

        plansza.solveGame();
        SudokuColumn column = plansza.getColumn(0);
        assertTrue(column.verify());

    }

    @Test
    void getBoxTest() {

        plansza.solveGame();
        SudokuBox row = plansza.getBox(0,0);
        assertTrue(row.verify());

    }


    @Test
    void getterAndSetterPointTest() {

        plansza.set(0, 0, 5);
        assertEquals(plansza.get(0, 0), 5);

        plansza.set(-1, 0, 5);
        assertNotEquals(plansza.get(-1, 0), 5);

        plansza.set(0, -1, 5);
        assertNotEquals(plansza.get(0, -1), 5);

        plansza.set(0, 0, -5);
        assertNotEquals(plansza.get(0, 0), -5);

        plansza.set(10, 0, 5);
        assertNotEquals(plansza.get(10, 0), 5);

        plansza.set(0, 10, 5);
        assertNotEquals(plansza.get(0, 10), 5);

        plansza.set(0, 0, 15);
        assertNotEquals(plansza.get(0, 0), 15);

    }

    @Test
    void testEquals() {
        assertEquals(plansza, plansza);
        assertEquals(plansza, plansza2);
        plansza.solveGame();
        plansza2.solveGame();
        assertNotEquals(plansza, solver);
        assertNotEquals(plansza, plansza2);
        assertNotEquals(plansza,null);
    }

    @Test
    void testHashCode() {
        assertEquals(plansza.hashCode(), plansza2.hashCode());
        plansza.solveGame();
        plansza2.solveGame();
        assertNotEquals(plansza.hashCode(), plansza2.hashCode());
    }

    @Test
    void testEqualsAndHashCodeContract() {
        assertTrue(plansza.equals(plansza2) && (plansza.hashCode() == plansza2.hashCode()));
        plansza.solveGame();
        assertEquals((plansza.hashCode() == plansza2.hashCode()), plansza.equals(plansza2));
    }

    @Test
    void testToString() {
        assertNotNull(plansza.toString());
    }

    @Test
    void cloneTest() throws CloneNotSupportedException {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        board.solveGame();
        SudokuBoard cloned = board.clone();
        assertNotSame(board,cloned);
        assertEquals(board,cloned);
    }

    @Test
    void testowo() {
        Float _a = 3_2.4_3__4356_3f;

        System.out.println(_a.equals(null));
    }

}
