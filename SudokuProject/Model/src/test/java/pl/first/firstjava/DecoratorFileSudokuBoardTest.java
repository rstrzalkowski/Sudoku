package pl.first.firstjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import pl.first.firstjava.exception.IncorrectFileException;


class DecoratorFileSudokuBoardTest {

    private final SudokuSolver solver = new BacktrackingSudokuSolver();
    private final SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
    private final SudokuBoard board1 = new SudokuBoard(solver);
    private final SudokuBoard board2 = new SudokuBoard(solver);
    private SudokuBoard board3;
    private SudokuBoard board4;

    @AfterEach
    void deleteFile() {
        File file = new File("board.txt");
        file.delete();
    }

    @Test
    void writeAndReadTest() {
        board1.solveGame();
        board2.solveGame();
        try (DecoratorFileSudokuBoard boardDao = (DecoratorFileSudokuBoard) factory
                .getDecoFileDao("board.txt")) {
            boardDao.write(board1,board2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (DecoratorFileSudokuBoard boardDao = (DecoratorFileSudokuBoard) factory
                .getDecoFileDao("board.txt")) {
            SudokuBoard[] both = boardDao.readBoth();
            board3 = both[0];
            board4 = both[1];
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(board1,board3);
        assertEquals(board2,board4);
    }

    @Test
    void exceptionTest() {
        DecoratorFileSudokuBoard boardDao = (DecoratorFileSudokuBoard) factory
                .getDecoFileDao("null.txt");
        Exception exc2 = assertThrows(
                IncorrectFileException.class, boardDao::readBoth);

    }
}