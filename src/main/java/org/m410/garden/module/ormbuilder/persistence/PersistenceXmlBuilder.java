package org.m410.garden.module.ormbuilder.persistence;

import org.m410.garden.configuration.Configuration;
import org.m410.garden.configuration.PersistenceDefinition;
import org.m410.garden.module.ormbuilder.orm.ConfigFileBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Creates the persistence xml with the configuration properties set in the garden.fab.yml
 *
 * @author Michael Fortin
 */
public class PersistenceXmlBuilder implements ConfigFileBuilder {

    public String make(Configuration configuration) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        PersistenceDefinition definition = configuration.getPersistence().stream().filter((d)->{
            return d.getName().equalsIgnoreCase("garden-jpa");
        }).findAny().orElseThrow(() -> new RuntimeException("JPA persistence configuration not found"));

        Document doc = docBuilder.newDocument();
        Element root = doc.createElementNS("http://xmlns.jcp.org/xml/ns/persistence", "persistence");
        root.setAttribute("version", "2.1");
        root.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:schemaLocation",
                "http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_1.xsd");
        doc.appendChild(root);

        Element persistUnit = doc.createElement("persistence-unit");
        persistUnit.setAttribute("name",definition.getName());
        persistUnit.setAttribute("transaction-type","RESOURCE_LOCAL");

        Element provider = doc.createElement("provider");
        provider.setTextContent("org.hibernate.jpa.HibernatePersistenceProvider");
        persistUnit.appendChild(provider);

        Element mappingFile = doc.createElement("mapping-file");
        mappingFile.setTextContent("META-INF/orm.xml");
        persistUnit.appendChild(mappingFile);

        definition.getClasses().stream().forEach(s ->{
            Element propElem = doc.createElement("class");
            propElem.setTextContent(s);
            persistUnit.appendChild(propElem);
        });

        Element propertiesElem = doc.createElement("properties");
        definition.getProperties().forEach((k,v)->{
            Element propElem = doc.createElement("property");
            propElem.setAttribute("name",k);
            propElem.setAttribute("value",v);
            propertiesElem.appendChild(propElem);
        });
        persistUnit.appendChild(propertiesElem);
        root.appendChild(persistUnit);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);


        // validation
        try {
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            URL schemaURL = new URL("http://xmlns.jcp.org/xml/ns/persistence/persistence_2_1.xsd");
            Schema schema = sf.newSchema(schemaURL);
            Validator validator = schema.newValidator();
            validator.validate(source);
        }
        catch (SAXException | IOException e) {
            System.out.println("WARNING: Did not validate: " + e.getMessage());
        }


        StringWriter s = new StringWriter();
        StreamResult result = new StreamResult(s);
        transformer.transform(source, result);

        return s.toString();
    }


    public void writeToFile(Path path, Configuration configuration) {
        try {
            Files.write(path, make(configuration).getBytes());
        }
        catch (Exception e) {
            throw new RuntimeException("Could not write to path: " + path, e);
        }
    }
}
