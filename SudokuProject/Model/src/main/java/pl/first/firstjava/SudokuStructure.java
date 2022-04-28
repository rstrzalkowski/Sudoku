package pl.first.firstjava;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * Klasa, z której dziedziczą SudokuRow, SudokuColumn i SudokuBox.
 */
public abstract class SudokuStructure {

    protected SudokuField[] fields;

    public SudokuStructure(SudokuField[] fields) {
        this.fields = fields;
    }

    /**
     * Metoda sprawdzająca, poprawność wypełnienia struktury.
     * @return zwraca true jeśli jest poprawnie, false jeśli nie
     */
    public boolean verify() {
        if (fields[8].getFieldValue() == 0) {
            return false;
        }

        for (int i = 0;i < 8;i++) {
            for (int j = i + 1;j < 9;j++) {
                if (fields[i].getFieldValue() ==  fields[j].getFieldValue()
                        || fields[i].getFieldValue() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SudokuStructure that = (SudokuStructure) o;

        return new EqualsBuilder().append(fields, that.fields).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(fields).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("fields", fields)
                .toString();
    }


}
