package cain.josh;

import java.lang.String;import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class PostPageRenderer {

    private static final String TEMPLATE =
            "<html>\n" +
            "<head><title>HTTP Post Binding</title></head>\n" +
            "<body onload=\"document.forms[0].submit()\">\n" +
            "<form method=\"POST\" action=\"%s\">\n" +
            "    %s" +
            "    <noscript>\n" +
            "        <P>Javascript is not enabled in your browser.  Please enable javascript and try again, or click the button below to continue.</P>\n" +
            "        <input type=\"submt\" value=\"continue\"/>\n" +
            "    </noscript>\n" +
            "</form>\n" +
            "</body>\n" +
            "</html>";

    private final String postActionLocation;
    private final BiFunction<String, String, String> formInputRenderer;

    public PostPageRenderer(final String postActionLocation) {
        Objects.requireNonNull(postActionLocation, "Cannot perform post renderings without supplying an action location");

        this.postActionLocation = postActionLocation;
        formInputRenderer = new FormInputRenderer();
    }

    public String renderPostback(final Map<String, String> postParams) {
        Objects.requireNonNull(postParams);

        final String formParams = postParams.entrySet().stream()
                .map(entry -> formInputRenderer.apply(entry.getKey(), entry.getValue())).collect(Collectors.joining("\t"));
        return String.format(TEMPLATE, postActionLocation, formParams);
    }
}
