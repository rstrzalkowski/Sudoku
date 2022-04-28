package pl.first.firstjava;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import pl.first.firstjava.exception.IncorrectFileException;

public class DecoratorFileSudokuBoard extends FileSudokuBoardDao {

    public DecoratorFileSudokuBoard(String filename) {
        super(filename);
    }

    public SudokuBoard[] readBoth() throws IncorrectFileException {
        SudokuBoard actual = null;
        SudokuBoard original = null;
        SudokuBoard[] both = new SudokuBoard[2];

        try (FileInputStream fileInputStream = new FileInputStream(filename);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            actual = (SudokuBoard) objectInputStream.readObject();
            original = (SudokuBoard) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new IncorrectFileException(e);
        }
        both[0] = actual;
        both[1] = original;

        return both;
    }

    public void write(SudokuBoard sudokuBoard1, SudokuBoard sudokuBoard2)
            throws IncorrectFileException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filename);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(sudokuBoard1);
            objectOutputStream.writeObject(sudokuBoard2);
        } catch (IOException e) {
            throw new IncorrectFileException(e);
        }
    }

}
