package com.spannerinworks.xmldsl;

import org.junit.Test;
import org.xmlunit.xpath.JAXPXPathEngine;
import org.xmlunit.xpath.XPathEngine;

import static com.spannerinworks.xmldsl.XmlDsl.*;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.xmlunit.builder.Input.fromString;

public class XmlDslTest {
    XPathEngine xpath = new JAXPXPathEngine();

    @Test
    public void singleRoot() throws Exception {
        String xml = xmlDsl(elem("root")).toXmlString();
        assertThat(xpath.evaluate("count(//root)", fromString(xml).build()), is("1"));
    }

    @Test
    public void attributes() throws Exception {
        String xml = xmlDsl(elem("root", attr("some", "stuff"))).toXmlString();
        assertThat(xpath.evaluate("/root/@some", fromString(xml).build()), is("stuff"));
    }

    @Test
    public void children() throws Exception {
        String xml = xmlDsl(
                elem("root").with(
                        elem("child1").with(
                                elem("grandchild")
                        ),
                        elem("child2")
                )
        ).toXmlString();

        assertThat(xpath.evaluate("count(/root/child1)", fromString(xml).build()), is("1"));
        assertThat(xpath.evaluate("count(/root/child2)", fromString(xml).build()), is("1"));
        assertThat(xpath.evaluate("count(/root/child1/grandchild)", fromString(xml).build()), is("1"));
    }

    @Test
    public void textNodes() throws Exception {
        String xml = xmlDsl(
                elem("root").with(
                        text("the>"),
                        elem("piggy"),
                        text("<middle")
                )
        ).toXmlString();

        assertThat(xpath.evaluate("string(/root)", fromString(xml).build()), is("the><middle"));
        assertThat(xpath.evaluate("count(/root/piggy)", fromString(xml).build()), is("1"));
    }

    @Test
    public void omitXmlDeclaration() throws Exception {
        XmlDsl xmlDsl = xmlDsl(elem("root"));

        assertThat(xmlDsl.toXmlString(false), matchesPattern("<\\?xml.*\\?><root/>"));
        assertThat(xmlDsl.toXmlString(true), is("<root/>"));

    }
}
