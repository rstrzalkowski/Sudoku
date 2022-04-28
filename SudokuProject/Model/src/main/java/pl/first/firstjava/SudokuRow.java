package pl.first.firstjava;

/**
 * Klasa wiersza, dziedziczy z SudokuStructure.
 */
public class SudokuRow extends SudokuStructure implements Cloneable {
    public SudokuRow(SudokuField[] fields) {
        super(fields);
    }

    @Override
    public SudokuRow clone() throws CloneNotSupportedException {
        SudokuRow clone = (SudokuRow) super.clone();
        SudokuField[] newFields = new SudokuField[9];
        for (int i = 0;i < 9;i++) {
            newFields[i] = new SudokuField();
            newFields[i] = this.fields[i];
        }
        clone.fields = newFields;
        return clone;
    }
}
