package pl.first.firstjava;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class BacktrackingSudokuSolver implements SudokuSolver {

    private final List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

    /**
     * Metoda wypełniająca planszę sudoku zgodnie z zasadami gry.
     * @param board plansza, która będzie wypełniona
     */
    public void solve(final SudokuBoard board) {
        backtrack(board);
    }

    /**
     * Algorytm backtrackingu, który sprawdza, czy można wpisać kolejne liczby z wymieszanej listy
     * na planszy sudoku, jeśli tak to wpisuje.
     * @param board plansza, na której ma zostać przeprowadzony algorytm
     * @return wartość zwracana przez metodę służy do rekurencji
     */
    private boolean backtrack(SudokuBoard board) {

        List<Integer> numbersToShuffle = numbers;
        Collections.shuffle(numbersToShuffle);

        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                if (board.get(row,column) == 0) {
                    for (int index = 0;index < 9;index++) {
                        if (goodNumber(numbers.get(index), row, column,board)) {
                            board.set(row,column,numbers.get(index));

                            if (backtrack(board)) {
                                return true;
                            } else {
                                board.set(row,column,0);
                            }
                        }
                    }
                    return false;
                }

            }
        }
        return true;
    }

    /**
     * Metoda sprawdzająca, czy w danym wierszu występuje już dana liczba.
     * @param number parametr określający numer, który ma zostać sprawdzony
     * @param row parametr określający numer wiersza na planszy
     * @param board plansza, w której sprawdzamy występowanie liczby
     * @return zwracana jest prawda, jeśli nie znaleziono danego numeru w wierszu
     */
    private boolean rowCheck(int number, int row, SudokuBoard board) {
        for (int column = 0;column < 9;column++) {
            if (number == board.get(row,column)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Metoda sprawdzająca, czy w danej kolumnie występuje już dana liczba.
     * @param number parametr określający numer, który ma zostać sprawdzony
     * @param column parametr określający numer kolumny na planszy
     * @param board plansza, w której sprawdzamy występowanie liczby
     * @return zwracana jest prawda, jeśli nie znaleziono danego numeru w kolumnie
     */
    private boolean columnCheck(int number, int column, SudokuBoard board) {
        for (int row = 0;row < 9;row++) {
            if (number == board.get(row,column)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Metoda sprawdzająca, czy w kwadracie 3x3, w której jest dany punkt, jest już dana liczba.
     * @param number parametr określający numer, który ma zostać sprawdzony
     * @param row parametr określający numer wiersza na planszy
     * @param column  parametr określający numer kolumny na planszy
     * @param board plansza, w której sprawdzamy występowanie liczby
     * @return zwracana jest prawda, jeśli nie znaleziono danego numeru w kwadracie 3x3
     */
    private boolean boxCheck(int number, int row, int column, SudokuBoard board) {
        int startingRow = row - row % 3;
        int startingColumn = column - column % 3;

        for (int i = startingRow;i < startingRow + 3;i++) {
            for (int j = startingColumn;j < startingColumn + 3;j++) {
                if (number == board.get(i,j)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Metoda, która sprawdza, czy na daną pozycję można wpisać dany numer.
     * @param number parametr określający numer, który ma zostać sprawdzony
     * @param row parametr określający numer wiersza na planszy
     * @param column  parametr określający numer kolumny na planszy
     * @param board plansza, w której sprawdzamy występowanie liczby
     * @return zwracana jest prawda jest numer można wpisać na danej pozycji
     */
    private boolean goodNumber(int number, int row, int column, SudokuBoard board) {
        if (!rowCheck(number,row,board)) {
            return false;
        }
        if (!columnCheck(number,column,board)) {
            return false;
        }
        return boxCheck(number, row, column, board);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BacktrackingSudokuSolver that = (BacktrackingSudokuSolver) o;

        return new EqualsBuilder().append(numbers, that.numbers).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(numbers).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("numbers", numbers)
                .toString();
    }

}
