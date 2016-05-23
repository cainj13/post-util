package cain.josh;

import java.util.function.BiFunction;
import java.util.function.Function;

public class FormInputRenderer implements BiFunction<String, String, String> {

    private static final Function<String, String> emptyForNull = new EmptyForNull();
    private static final String TEMPLATE = "<input type=\"hidden\" name=\"%s\" value=\"%s\"/>\n";

    @Override
    public String apply(final String name, final String value) {
        return String.format(TEMPLATE, emptyForNull.apply(name), emptyForNull.apply(value));
    }
}
