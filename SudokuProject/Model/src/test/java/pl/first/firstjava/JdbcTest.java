package pl.first.firstjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import pl.first.firstjava.exception.DaoException;
import pl.first.firstjava.exception.DatabaseException;

public class JdbcTest {

    private SudokuSolver solver = new BacktrackingSudokuSolver();
    private SudokuBoard board1 = new SudokuBoard(solver);
    private SudokuBoard board2 = new SudokuBoard(solver);
    private SudokuBoard board3 = new SudokuBoard(solver);
    private SudokuBoard board4 = new SudokuBoard(solver);

    @Test
    void writeAndReadTest() {

        board1.solveGame();
        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();

        try (Dao<SudokuBoard> jdbc1 = factory.getJdbcDao("board1");
            Dao<SudokuBoard> jdbc2 = factory.getJdbcDao("board2");
            Dao<SudokuBoard> jdbc3 = factory.getJdbcDao(null)) {

            jdbc1.write(board1);
            jdbc2.write(board2);

            board3 = jdbc1.read();
            board4 = jdbc2.read();

            assertThrows(DatabaseException.class, () -> jdbc3.write(board1));

        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(board1,board3);
        assertEquals(board2,board4);

        assertNotSame(board1,board3);
        assertNotSame(board2,board4);

    }

}
