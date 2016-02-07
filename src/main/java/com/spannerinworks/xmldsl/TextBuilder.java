package com.spannerinworks.xmldsl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class TextBuilder implements ChildBuilder {
    private String value;

    public TextBuilder(String value) {
        this.value = value;
    }

    @Override
    public void build(Document document, Element parent) {
        Text textNode = document.createTextNode(this.value);
        parent.appendChild(textNode);
    }
}
