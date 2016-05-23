package cain.josh;

import java.util.function.Function;

public class EmptyForNull implements Function<String, String> {

    private static final Function<String, String> INSTANCE = new EmptyForNull();

    @Override
    public String apply(String s) {
        return s == null ? "" : s;
    }
}
