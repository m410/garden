package org.m410.j8.module.ormbuilder.persistence;

import org.m410.j8.configuration.Configuration;
import org.m410.j8.configuration.PersistenceDefinition;
import org.m410.j8.module.ormbuilder.orm.ConfigFileBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Creates the persistence xml with the configuration properties set in the configuration.m410.yml
 *
 * @author Michael Fortin
 */
public class PersistenceXmlBuilder implements ConfigFileBuilder {

    public String make(Configuration configuration) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        PersistenceDefinition definition = configuration.getPersistence().stream().filter((d)->{
            return d.getName().equalsIgnoreCase("m410-jpa");
        }).findAny().orElseThrow(() -> new RuntimeException("JPA persistence definition not found"));

        Document doc = docBuilder.newDocument();
        Element root = doc.createElementNS("http://java.sun.com/xml/ns/persistence", "persistence");
        root.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:schemaLocation",
                "http://java.sun.com/xml/ns/persistence" +
                        " http://java.sun.com/xml/ns/persistence/persistence_1_0.xsd");
        root.setAttribute("version", "1.0");
        doc.appendChild(root);

        Element persistUnit = doc.createElement("persistence-unit");
        persistUnit.setAttribute("name",definition.getName());
        persistUnit.setAttribute("transaction-type","RESOURCE_LOCAL");

        Element provider = doc.createElement("provider");
        provider.setTextContent("org.hibernate.ejb.HibernatePersistence");
        persistUnit.appendChild(provider);

        Element mappingFile = doc.createElement("mapping-file");
        mappingFile.setTextContent("/META-INF/orm.xml");
        persistUnit.appendChild(mappingFile);

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

        StringWriter s = new StringWriter();
        StreamResult result = new StreamResult(s);
        transformer.transform(source, result);

        return s.toString();
    }


    public void writeToFile(Path path, Configuration configuration) {
        try {
            Files.write(path, make(configuration).getBytes());
        } catch (Exception e) {
            throw new RuntimeException("Could not write to path: " + path, e);
        }
    }
}
