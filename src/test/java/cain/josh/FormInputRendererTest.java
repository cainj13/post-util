package cain.josh;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.String;import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class FormInputRendererTest {
    private static final Logger log = LoggerFactory.getLogger(FormInputRendererTest.class);

    @Test
    public void shouldRenderFormInput() {
        final String renderedText = new FormInputRenderer().apply("foo", "bar");
        log.info("Rendered text: {}", renderedText);
        assertThat(renderedText, is(equalTo("<input type=\"hidden\" name=\"foo\" value=\"bar\"/>\n")));
    }

    @Test
    public void shouldRenderFormInputWhenTypeEmpty() {
        final String renderedText = new FormInputRenderer().apply(null, "bar");
        log.info("Rendered text: {}", renderedText);
        assertThat(renderedText, is(equalTo("<input type=\"hidden\" name=\"\" value=\"bar\"/>\n")));
    }

    @Test
    public void shouldRenderFormInputWhenValueEmpty() {
        final String renderedText = new FormInputRenderer().apply("foo", null);
        log.info("Rendered text: {}", renderedText);
        assertThat(renderedText, is(equalTo("<input type=\"hidden\" name=\"foo\" value=\"\"/>\n")));
    }
}
