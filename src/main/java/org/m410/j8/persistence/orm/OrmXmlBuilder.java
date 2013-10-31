package org.m410.j8.persistence.orm;

import org.m410.j8.configuration.Configuration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**

 *
 * @author Michael Fortin
 */
public final class OrmXmlBuilder implements ConfigFileBuilder{

    private List<Entity> entities = new ArrayList<>();

    public OrmXmlBuilder() {
    }

    public OrmXmlBuilder addEntity(Entity e) {
        entities.add(e);
        return this;
    }

    /**
     * http://wiki.eclipse.org/EclipseLink/Examples/JPA/EclipseLink-ORM.XML
     * http://wiki.eclipse.org/EclipseLink/Examples/JPA/EmployeeXML
     *
     * @return this OrmBuilder
     * @throws ParserConfigurationException
     * @throws TransformerException
     * @throws SAXException
     * @throws IOException
     */
    public String make() throws ParserConfigurationException, TransformerException, SAXException,
            IOException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.newDocument();
        final String nsUrl = "http://xmlns.jcp.org/xml/ns/persistence/orm";
        Element root = doc.createElementNS(nsUrl, "entity-mappings");
        root.setAttribute("version", "2.1");
        root.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance",
                "xsi:schemaLocation",
                "http://xmlns.jcp.org/xml/ns/persistence/orm " +
                        "http://xmlns.jcp.org/xml/ns/persistence/orm_2_1.xsd");
        doc.appendChild(root);

        Element meta = doc.createElement("persistence-unit-metadata");
        meta.appendChild(doc.createElement("xml-mapping-metadata-complete"));
        meta.appendChild(doc.createElement("exclude-default-mappings"));
        root.appendChild(meta);

        entities.stream().forEach(e->e.appendElement(doc,root));

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);

        // code to validate
//        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
//        URL schemaURL = new URL("http://www.eclipse.org/eclipselink/xsds/eclipselink_orm_2_1.xsd");
//        Schema schema = sf.newSchema(schemaURL);
//        Validator validator = schema.newValidator();
//        validator.validate(source);

        StringWriter s = new StringWriter();
        StreamResult result = new StreamResult(s); // new File("orm.xml")
        transformer.transform(source, result);

        return s.toString();
    }

    @Override
    public void writeToFile(Path path, Configuration configuration) {
        try {
            Files.write(path, make().getBytes());
        } catch (Exception e) {
            throw new RuntimeException("Couldn't write to path: " + path, e);
        }
    }

}
