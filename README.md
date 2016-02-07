Small Java DSL for creating little bits of XML.

DSL style blatantly stolen from java2html.

Use like this:
{code}
import static com.spannerinworks.xmldsl.XmlDsl.*

String xml = xmlDsl(
        elem("parent").with(
                elem("child1", attr("name", "junior").with(
                        elem("grandchild")
                ),
                elem("child2")
        )
).toXmlString();

{code}