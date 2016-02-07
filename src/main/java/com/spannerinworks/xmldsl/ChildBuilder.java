package com.spannerinworks.xmldsl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface ChildBuilder {
    void build(Document document, Element parent);
}
