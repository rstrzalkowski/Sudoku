package pl.first.firstjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;



class BacktrackingSudokuSolverTest {
    SudokuSolver solver = new BacktrackingSudokuSolver();
    SudokuSolver solver2 = new BacktrackingSudokuSolver();
    SudokuBoard plansza = new SudokuBoard(solver);
    SudokuBoard plansza2 = new SudokuBoard(solver);

    /**
     * Test sprawdzający, czy plansza jest poprawnie wypełniona.
     */

    @Test
    void solveTest() {
        plansza.solveGame();
        assertTrue(checkRow(plansza));
        assertTrue(checkColumn(plansza));
        assertTrue(checkBox(plansza));

    }

    /**
     * Test sprawdzający, czy 2 kolejne wywołania funkcji fillBoard generują różne układy plansz.
     */
    @Test
    public void differentBoardsTest() {
        boolean differs = false;
        plansza.solveGame();
        plansza2.solveGame();


        for (int i = 0;i < 9;i++) {
            if (plansza2.get(0,i) != plansza2.get(0,1)) {
                differs = true;
            }
        }
        assertTrue(differs);
    }

    @Test
    void testEquals() {
        assertEquals(solver, solver);
        assertEquals(solver, solver2);
        assertNotEquals(solver, plansza);
        assertNotEquals(solver,null);

    }

    @Test
    void testHashCode() {
        assertEquals(solver.hashCode(), solver2.hashCode());

    }

    @Test
    void testEqualsAndHashCodeContract() {
        assertTrue(solver.equals(solver2) && (solver.hashCode() == solver2.hashCode()));
        assertEquals(solver.equals(plansza), (solver.hashCode() == plansza.hashCode()));
    }

    @Test
    void testToString() {
        assertNotNull(solver.toString());
    }


    private boolean checkRow(SudokuBoard plansza) {
        int insertedNumber;
        int counter;
        boolean valid = true;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                counter = 0;
                insertedNumber = plansza.get(i,j);

                for (int k = 0;k < 9;k++) {
                    if (insertedNumber == plansza.get(i,k)) {
                        counter++;
                        if (counter > 1) {
                            valid = false;
                        }
                    }
                }
            }
        }
        return valid;
    }

    private boolean checkColumn(SudokuBoard plansza) {
        int insertedNumber;
        int counter;
        boolean valid = true;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                counter = 0;
                insertedNumber = plansza.get(i,j);

                for (int l = 0;l < 9;l++) {
                    if (insertedNumber == plansza.get(l,j)) {
                        counter++;
                        if (counter > 1) {
                            valid = false;
                        }
                    }
                }
            }
        }
        return valid;
    }


    private boolean checkBox(SudokuBoard plansza) {
        int insertedNumber;
        int counter;
        boolean valid = true;
        for (int i = 0;i < 9;i++) {
            for (int j = 0; j < 9; j++) {
                counter = 0;
                insertedNumber = plansza.get(i,j);
                int startingRow = i - i % 3;
                int startingColumn = j - j % 3;

                for (int y = startingRow;y < startingRow + 3;y++) {
                    for (int x = startingColumn;x < startingColumn + 3;x++) {
                        if (insertedNumber == plansza.get(y,x)) {
                            counter++;
                            if (counter > 1) {
                                valid = false;
                            }
                        }
                    }
                }
            }
        }
        return valid;
    }


}