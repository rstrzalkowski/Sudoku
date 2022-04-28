package pl.first.firstjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import pl.first.firstjava.exception.IncorrectFieldValueException;

public class SudokuFieldTest {
    SudokuField field = new SudokuField();
    SudokuField field2 = new SudokuField();

    @Test
    public void getFieldValueTest() {
        assertEquals(field.getFieldValue(), 0);
    }

    @Test
    public void setFieldValueTest() {
        field.setFieldValue(2);
        assertEquals(field.getFieldValue(), 2);

        field.setFieldValue(2);
        assertEquals(field.getFieldValue(), 2);

    }

    @Test
    public void setBadFieldValueTest() {
        Exception exc1 = assertThrows(
                IncorrectFieldValueException.class, () -> field.setFieldValue(-1));
        assertEquals(
                "Zla wartosc!", exc1.getMessage());

        Exception exc2 = assertThrows(
                IncorrectFieldValueException.class, () -> field.setFieldValue(10));
        assertEquals(
                "Zla wartosc!", exc2.getMessage());
    }


    @Test
    void testEquals() {
        assertEquals(field, field2);
        assertEquals(field, field);
        assertNotEquals(field, null);
        SudokuSolver solver = new BacktrackingSudokuSolver();
        assertNotEquals(field, solver);


    }

    @Test
    void testHashCode() {
        assertEquals(field.hashCode(), field2.hashCode());
        field.setFieldValue(3);
        assertNotEquals(field.hashCode(), field2.hashCode());

    }

    @Test
    void testEqualsAndHashCodeContract() {
        assertTrue(field.equals(field2) && (field.hashCode() == field2.hashCode()));
        field.setFieldValue(3);
        assertEquals((field.hashCode() == field2.hashCode()), field.equals(field2));
    }

    @Test
    void testToString() {
        assertNotNull(field.toString());
    }

    @Test
    void testClone() throws CloneNotSupportedException {
        SudokuField field = new SudokuField(2);
        SudokuField cloned = field.clone();
        assertTrue(field.getFieldValue() == cloned.getFieldValue());
        assertNotSame(field,cloned);

    }

    @Test
    void testCompareTo() {
        SudokuField field = new SudokuField(2);
        SudokuField field2 = new SudokuField(3);
        SudokuField field3 = new SudokuField(3);
        assertTrue(field.compareTo(field2) < 0);
        assertTrue(field2.compareTo(field) > 0);
        assertTrue(field2.compareTo(field3) == 0);
        Exception exc1 = assertThrows(
                NullPointerException.class, () -> field.compareTo(null));

    }

}
