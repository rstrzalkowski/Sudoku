package pl.first.firstjava;

import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import pl.first.firstjava.exception.IncorrectFieldValueException;


/**
 * Klasa reprezentująca pola na planszy SudokuBoard.
 */
public class SudokuField implements Serializable, Cloneable, Comparable<SudokuField> {

    private int value = 0;

    public SudokuField() {
    }

    public SudokuField(int value) {
        this.value = value;
    }

    /**
     * Getter.
     * @return zrwaca wartość pola
     */
    public int getFieldValue() {
        return value;
    }

    /**
     * Setter.
     * @param value wartość na jaką ma być zmienione pole
     */
    public void setFieldValue(int value) {
        if (value == getFieldValue()) {
            return;
        }
        if (value >= 0 && value <= 9) {
            this.value = value;
        } else {
            throw new IncorrectFieldValueException("Zla wartosc!");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SudokuField that = (SudokuField) o;

        return new EqualsBuilder().append(value, that.value).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(value).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("value", value)
                .toString();
    }

    @Override
    public int compareTo(SudokuField o) {

        return Integer.compare(this.value,o.value);
    }

    @Override
    public SudokuField clone() throws CloneNotSupportedException {
            SudokuField clone = (SudokuField) super.clone();
            return clone;
    }
}
