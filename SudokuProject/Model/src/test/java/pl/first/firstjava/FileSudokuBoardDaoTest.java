package pl.first.firstjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import pl.first.firstjava.exception.IncorrectFileException;

class FileSudokuBoardDaoTest {

    private final SudokuSolver solver = new BacktrackingSudokuSolver();
    private final SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
    private final SudokuBoard board = new SudokuBoard(solver);
    private SudokuBoard board2;

    @AfterEach
    void deleteFile() {
        File file = new File("board.txt");
        file.delete();
    }

    @Test
    void writeAndReadTest() {
        board.solveGame();
        try (Dao<SudokuBoard> boardDao = factory.getFileDao("board.txt")) {
            boardDao.write(board);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (Dao<SudokuBoard> boardDao = factory.getFileDao("board.txt")) {
            board2 = boardDao.read();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(board,board2);
    }

    @Test
    void exceptionTest() {
        Dao<SudokuBoard> boardDao = factory.getFileDao("null.txt");
        Exception exc2 = assertThrows(
                IncorrectFileException.class, boardDao::read);

    }

}