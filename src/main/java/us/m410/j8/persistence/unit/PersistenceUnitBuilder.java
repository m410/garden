package us.m410.j8.persistence.unit;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Document Me..
 *
 * http://wiki.eclipse.org/EclipseLink/Examples/JPA/EmployeeXML
 * http://stackoverflow.com/questions/2373369/define-named-query-in-orm-xml-with-jpa-and-hibernate
 *
 * @author Michael Fortin
 */
public class PersistenceUnitBuilder {
    private String name;
    private String transactionType;
    private String description;
    private Map<String, String> map = new HashMap<>();
    private String mappingFile;

    public PersistenceUnitBuilder name(String s) {
        this.name = s;
        return this;
    }

    public PersistenceUnitBuilder transactionType(String s) {
        this.transactionType = s;
        return this;
    }

    public PersistenceUnitBuilder description(String s) {
        this.description = s;
        return this;
    }

    public PersistenceUnitBuilder property(String name, String value) {
        map.put(name,value);
        return this;
    }

    public PersistenceUnitBuilder mappingFile(String name) {
        this.mappingFile = name.replaceAll("\\.","/") + "/orm.xml";
        return this;
    }

    public String make() throws TransformerException, ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.newDocument();
        final String nsUrl = "http://java.sun.com/xml/ns/persistence";
        Element root = doc.createElementNS(nsUrl, "persistence");
        root.setAttribute("version", "1.0");
        root.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance",
                "xsi:schemaLocation",
                "http://java.sun.com/xml/ns/persistence " +
                        "http://java.sun.com/xml/ns/persistence/persistence_1_0.xsd");
        doc.appendChild(root);
        Element persistenceUnit = doc.createElement("persistence-unit");
        persistenceUnit.setAttribute("name",name);
        persistenceUnit.setAttribute("transaction-type",transactionType);
        root.appendChild(persistenceUnit);

        Element desc = doc.createElement("description");
        desc.setTextContent(description);
        persistenceUnit.appendChild(desc);

        Element mapping = doc.createElement("mapping-file");
        mapping.setTextContent(mappingFile);
        persistenceUnit.appendChild(mapping);

        Element properties = doc.createElement("properties");
        map.forEach((n,v)->{
            Element nvp = doc.createElement("property");
            nvp.setAttribute("name",n);
            nvp.setAttribute("value",v);
            properties.appendChild(nvp);
        });
        persistenceUnit.appendChild(properties);


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
}
