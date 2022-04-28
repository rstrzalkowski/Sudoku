package pl.first.firstjava;

import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Klasa planszy do Sudoku.
 */
public class SudokuBoard implements Serializable, Cloneable {

    private final SudokuSolver sudokuSolver;
    private SudokuField[][] board = new SudokuField[9][9];

    public SudokuBoard(SudokuSolver sudokuSolver) {
        this.sudokuSolver = sudokuSolver;

        for (int i = 0;i < 9;i++) {
            for (int j = 0;j < 9;j++) {
                board[i][j] = new SudokuField();
            }
        }
    }

    /**
     * Metoda przekazująca odpowiedzialność
     * wypełnienia planszy zgodnie z zasadami gry do klasy SudokuSolver.
     */
    public void solveGame() {
        sudokuSolver.solve(this);
    }

    /**
     * Metoda, która zwraca numer znajdujący się w danym wierszu i kolumnie.
     * @param row parametr określający numer wiersza na planszy
     * @param column  parametr określający numer kolumny na planszy
     * @return zwracany jest numer znajdujący się na danej pozycji
     */
    public int get(int row, int column) {
        if (row >= 0 && row < 9 && column >= 0 && column < 9) {
            return board[row][column].getFieldValue();
        } else {
            return 0;
        }
    }

    /**
     * Metoda, która wpisuje podany numer na danej pozycji na planszy.
     * @param number parametr określający numer, który ma zostać sprawdzony
     * @param row parametr określający numer wiersza na planszy
     * @param column  parametr określający numer kolumny na planszy
     */
    public void set(int row, int column, int number) {
        if (row >= 0 && row < 9 && column >= 0 && column < 9 && number >= 0 && number < 10) {
            board[row][column].setFieldValue(number);
        }
        checkBoard();
    }


    /**
     * Metoda sprawdzająca poprawność wypełnienia planszy.
     * @return zwraca true jeśli jest poprawnie, false jeśli nie
     */
    public boolean checkBoard() {

        for (int i = 0;i < 9;i++) {
            if (!getRow(i).verify()) {
                return false;
            }

            if (!getColumn(i).verify()) {
                return false;
            }

            for (int j = 0;j < 9;j += 3) {
                if (!getBox(i,j).verify()) {
                    return false;
                }
            }

        }
        return true;
    }


    /**
     * Metoda generująca obiekt konkretnego wiersza.
     * @param row numer wiersza
     * @return zwraca obiekt SudokuRow
     */
    public SudokuRow getRow(int row) {
        SudokuField[] fields = new SudokuField[9];
        System.arraycopy(board[row], 0, fields, 0, 9);

        return new SudokuRow(fields);
    }

    /**
     * Metoda generująca obiekt konkretnej kolumny.
     * @param column numer kolumny
     * @return zwraca obiekt SudokuColumn
     */
    public SudokuColumn getColumn(int column) {
        SudokuField[] fields = new SudokuField[9];
        for (int row = 0;row < 9;row++) {
            fields[row] = board[row][column];
        }

        return new SudokuColumn(fields);
    }

    /**
     * Metoda generująca obiekt konkretnego boxa.
     * @param row wiersz, w którym znajduje się dane pole
     * @param column kolumna, w której znajduje się dane pole
     * @return zwraca obiekt SudokuBox
     */
    public SudokuBox getBox(int row, int column) {
        int counter = 0;
        SudokuField[] fields = new SudokuField[9];
        int startingRow = row - row % 3;
        int startingColumn = column - column % 3;

        for (int i = startingRow; i < startingRow + 3;i++) {
            for (int j = startingColumn;j < startingColumn + 3;j++) {
                fields[counter++] = board[i][j];
            }
        }
        return new SudokuBox(fields);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SudokuBoard that = (SudokuBoard) o;

        return new EqualsBuilder().append(board, that.board).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(board).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("sudokuSolver", sudokuSolver)
                .append("board", board)
                .toString();
    }

    @Override
    public SudokuBoard clone() throws CloneNotSupportedException {
        SudokuBoard newBoard = (SudokuBoard) super.clone();
        SudokuField[][] newFields = new SudokuField[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                newFields[i][j] = this.board[i][j].clone();
            }
        }
        newBoard.board = newFields;
        return newBoard;
    }
}

