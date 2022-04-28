package pl.first.firstjava;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class SudokuBoardDaoFactoryTest {

    private SudokuBoardDaoFactory daofactory = new SudokuBoardDaoFactory();

    @Test
    void getFileDaoTest() {
        assertNotNull(daofactory.getFileDao("board"));
    }
}