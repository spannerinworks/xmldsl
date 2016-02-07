package com.spannerinworks.xmldsl;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.io.Writer;

public class XmlDsl {
    private ElemBuilder root;

    public XmlDsl(ElemBuilder root) {
        this.root = root;
    }

    public static XmlDsl xmlDsl(ElemBuilder root) {
        return new XmlDsl(root);
    }

    public static ElemBuilder elem(String name, AttrPair... attrs) {
        return new ElemBuilder(name, attrs);
    }

    public static AttrPair attr(String name, String value) {
        return new AttrPair(name, value);
    }

    public static TextBuilder text(String value) {
        return new TextBuilder(value);
    }

    public String toXmlString() throws ParserConfigurationException, TransformerException {
        return toXmlString(false);
    }

    public String toXmlString(boolean omitXmlDeclaration) throws TransformerException, ParserConfigurationException {
        StringWriter writer = new StringWriter();

        writeXml(writer, omitXmlDeclaration);

        return writer.toString();
    }

    public void writeXml(Writer writer, boolean omitXmlDeclaration) throws ParserConfigurationException, TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        Document document = toDocument();
        if (omitXmlDeclaration) {
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        }

        DOMSource source = new DOMSource(document);

        Result target = new StreamResult(writer);
        transformer.transform(source, target);
    }

    public Document toDocument() throws ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        Document document = documentBuilderFactory.newDocumentBuilder().newDocument();

        root.build(document);
        return document;
    }

}
