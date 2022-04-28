package pl.first.firstjava;

/**
 * Klasa kolumny, dziedziczy z SudokuStructure.
 */
public class SudokuColumn extends SudokuStructure implements Cloneable {
    public SudokuColumn(SudokuField[] fields) {
        super(fields);
    }

    @Override
    public SudokuColumn clone() throws CloneNotSupportedException {
        SudokuColumn clone = (SudokuColumn) super.clone();
        SudokuField[] newFields = new SudokuField[9];
        for (int i = 0;i < 9;i++) {
            newFields[i] = new SudokuField();
            newFields[i] = this.fields[i];
        }
        clone.fields = newFields;
        return clone;
    }
}
