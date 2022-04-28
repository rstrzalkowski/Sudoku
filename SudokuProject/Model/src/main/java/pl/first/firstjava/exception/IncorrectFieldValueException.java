package pl.first.firstjava.exception;

/**
 * Wyjątek rzucany przy próbie zmiany SudokuField na niepoprawną wartość.
 */
public class IncorrectFieldValueException extends IllegalArgumentException {
    public IncorrectFieldValueException(final String msg) {
        super(msg);
    }

}