package com.spannerinworks.xmldsl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ElemBuilder implements ChildBuilder {
    private String name;
    private AttrPair[] attrs = new AttrPair[0];
    private ChildBuilder[] children = new ChildBuilder[0];

    public ElemBuilder(String name, AttrPair... attrs) {
        this.name = name;
        this.attrs = attrs;
    }

    public ElemBuilder with(ChildBuilder... children) {
        this.children = children;
        return this;
    }


    public void build(Document document, Element parent) {
        parent.appendChild(buildElement(document));

    }

    public void build(Document document) {
        document.appendChild(buildElement(document));
    }

    public Element buildElement(Document document) {
        Element element = document.createElement(name);
        for (AttrPair attr : attrs) {
            element.setAttribute(attr.name, attr.value);
        }
        for (ChildBuilder child : children) {
            child.build(document, element);
        }
        return element;
    }

}
