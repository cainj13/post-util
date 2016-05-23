package cain.josh;

import org.custommonkey.xmlunit.HTMLDocumentBuilder;
import org.custommonkey.xmlunit.TolerantSaxDocumentBuilder;
import org.custommonkey.xmlunit.XMLAssert;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import java.lang.Exception;import java.lang.NullPointerException;import java.lang.String;import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PostPageRendererTest {
    private static final Logger log = LoggerFactory.getLogger(PostPageRendererTest.class);

    @Test (expected = NullPointerException.class)
    public void shouldAlwaysRequireLocation() {
        new PostPageRenderer(null);
    }

    @Test
    public void shouldRenderPostPageWithFormInput() throws Exception {
        final Map<String, String> postParams = new HashMap<>();
        postParams.put("param1", "foo");
        postParams.put("param2", "bar");
        postParams.put("param3", "baz");

        final String postActionLocation = "http://localhost:8080/auth";
        final String renderedString = new PostPageRenderer(postActionLocation).renderPostback(postParams);
        log.info(renderedString);

        final TolerantSaxDocumentBuilder tolerantSaxDocumentBuilder = new TolerantSaxDocumentBuilder(XMLUnit.getTestParser());
        final HTMLDocumentBuilder htmlDocumentBuilder = new HTMLDocumentBuilder(tolerantSaxDocumentBuilder);
        final Document wellFormedDocument = htmlDocumentBuilder.parse(renderedString);

        XMLAssert.assertXpathEvaluatesTo(postActionLocation, "/html/body/form/@action", wellFormedDocument);
        XMLAssert.assertXpathEvaluatesTo("foo", "/html/body/form/input[@name=\"param1\"]/@value", wellFormedDocument);
        XMLAssert.assertXpathEvaluatesTo("bar", "/html/body/form/input[@name=\"param2\"]/@value", wellFormedDocument);
    }

    @Test
    public void shouldRenderPostPageWithNoInput() throws Exception {
        final Map<String, String> postParams = Collections.emptyMap();

        final String postActionLocation = "http://localhost:8080/auth";
        final String renderedString = new PostPageRenderer(postActionLocation).renderPostback(postParams);
        log.info(renderedString);

        final TolerantSaxDocumentBuilder tolerantSaxDocumentBuilder = new TolerantSaxDocumentBuilder(XMLUnit.getTestParser());
        final HTMLDocumentBuilder htmlDocumentBuilder = new HTMLDocumentBuilder(tolerantSaxDocumentBuilder);
        final Document wellFormedDocument = htmlDocumentBuilder.parse(renderedString);

        XMLAssert.assertXpathEvaluatesTo(postActionLocation, "/html/body/form/@action", wellFormedDocument);
        XMLAssert.assertXpathNotExists("/html/body/form/input[@type=\"hidden\"]", wellFormedDocument);
    }
}
