package pl.first.firstjava;

import pl.first.firstjava.exception.DatabaseException;

public class SudokuBoardDaoFactory {
    public Dao<SudokuBoard> getFileDao(String filename) {
        return new FileSudokuBoardDao(filename);
    }

    public Dao<SudokuBoard> getDecoFileDao(String filename) {
        return new DecoratorFileSudokuBoard(filename);
    }

    public Dao<SudokuBoard> getJdbcDao(String filename) throws DatabaseException {
        return new JdbcSudokuBoardDao(filename);
    }
}
