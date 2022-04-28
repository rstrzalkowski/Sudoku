package pl.first.firstjava;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import pl.first.firstjava.exception.IncorrectFileException;

public class FileSudokuBoardDao implements Dao<SudokuBoard> {

    protected final String filename;

    public FileSudokuBoardDao(String filename) {
        this.filename = filename;
    }

    @Override
    public SudokuBoard read() throws IncorrectFileException {
        SudokuBoard sudokuBoard = null;

        try (FileInputStream fileInputStream = new FileInputStream(filename);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
             sudokuBoard = (SudokuBoard) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new IncorrectFileException(e);
        }

        return sudokuBoard;
    }

    @Override
    public void write(SudokuBoard sudokuBoard) throws IncorrectFileException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filename);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
             objectOutputStream.writeObject(sudokuBoard);
        } catch (IOException e) {
            throw new IncorrectFileException(e);
        }
    }


    public void close() {
    }
}
