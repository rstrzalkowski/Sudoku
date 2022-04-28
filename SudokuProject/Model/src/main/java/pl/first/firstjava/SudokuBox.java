package pl.first.firstjava;

/**
 * Klasa boxa, dziedziczy z SudokuStructure.
 */
public class SudokuBox extends SudokuStructure implements Cloneable {
    public SudokuBox(SudokuField[] fields) {
        super(fields);
    }

    @Override
    public SudokuBox clone() throws CloneNotSupportedException {
        SudokuBox clone = (SudokuBox) super.clone();
        SudokuField[] newFields = new SudokuField[9];
        for (int i = 0;i < 9;i++) {
            newFields[i] = new SudokuField();
            newFields[i] = this.fields[i];
        }
        clone.fields = newFields;
        return clone;
    }
}
