package com.example.view;

import java.util.regex.Pattern;
import javafx.util.StringConverter;

public class ModifiedStringConverter extends StringConverter<Integer> {
    public ModifiedStringConverter() {
    }

    private Pattern pattern = Pattern.compile("[0-9]");

        @Override
        public String toString(Integer integer) {
            return integer.toString();
        }

        @Override
        public Integer fromString(String string) {
            if (pattern.matcher(string).matches()) {
                return Integer.valueOf(string);
            } else {
                return 0;
            }
        }
}
