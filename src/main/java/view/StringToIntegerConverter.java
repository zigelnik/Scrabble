package view;

import javafx.util.StringConverter;

public class StringToIntegerConverter extends StringConverter<Number> {

    @Override
    public String toString(Number number) {
        if (number == null) {
            return "";
        }
        return number.toString();
    }

    @Override
    public Number fromString(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}