package org.m410.garden.module.ormbuilder.persistence;

import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;
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
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * Creates the persistence xml with the configuration properties set in the garden.fab.yml
 *
 * @author Michael Fortin
 */
public class PersistenceXmlBuilder implements ConfigFileBuilder {

    public String make(ImmutableHierarchicalConfiguration configuration) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        final Iterable<String> keys = configuration::getKeys;
        final String persistenceKey = StreamSupport.stream(keys.spliterator(), false)
                .filter(key -> key.matches("persistence\\(org\\.m410\\.garden:garden-jpa.*?\\)"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("JPA persistence configuration not found"));;

        final ImmutableHierarchicalConfiguration config = configuration.immutableConfigurationAt(persistenceKey);

        final Document doc = docBuilder.newDocument();
        final Element root = doc.createElementNS("http://xmlns.jcp.org/xml/ns/persistence", "persistence");
        root.setAttribute("version", "2.1");
        root.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:schemaLocation",
                "http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_1.xsd");
        doc.appendChild(root);

        final Element persistUnit = doc.createElement("persistence-unit");
        persistUnit.setAttribute("name",config.getString("unit_name"));
        persistUnit.setAttribute("transaction-type","RESOURCE_LOCAL");

        final Element provider = doc.createElement("provider");
        provider.setTextContent("org.hibernate.jpa.HibernatePersistenceProvider");
        persistUnit.appendChild(provider);

        final Element mappingFile = doc.createElement("mapping-file");
        mappingFile.setTextContent("META-INF/orm.xml");
        persistUnit.appendChild(mappingFile);

        config.getList(String.class, "classes").forEach(s ->{
            Element propElem = doc.createElement("class");
            propElem.setTextContent(s);
            persistUnit.appendChild(propElem);
        });

        final Element propertiesElem = doc.createElement("properties");
        final ImmutableHierarchicalConfiguration props = config.immutableConfigurationAt("properties");
        final Iterable<String> propKeys = props::getKeys;
        StreamSupport.stream(propKeys.spliterator(), false).forEach((k)->{
            Element propElem = doc.createElement("property");
            propElem.setAttribute("name",k);
            propElem.setAttribute("value",props.getString(k));
            propertiesElem.appendChild(propElem);
        });
        persistUnit.appendChild(propertiesElem);
        root.appendChild(persistUnit);

        final TransformerFactory transformerFactory = TransformerFactory.newInstance();
        final Transformer transformer = transformerFactory.newTransformer();
        final DOMSource source = new DOMSource(doc);


        // validation
        try {
            final SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            final URL schemaURL = new URL("http://xmlns.jcp.org/xml/ns/persistence/persistence_2_1.xsd");
            final Schema schema = sf.newSchema(schemaURL);
            final Validator validator = schema.newValidator();
            validator.validate(source);
        }
        catch (SAXException | IOException e) {
            System.out.println("WARNING: Did not validate: " + e.getMessage());
        }

        final StringWriter s = new StringWriter();
        final StreamResult result = new StreamResult(s);
        transformer.transform(source, result);

        return s.toString();
    }



    public void writeToFile(Path path, ImmutableHierarchicalConfiguration configuration) {
        final File parentDirectory = path.getParent().toFile();

        if( parentDirectory.exists() || parentDirectory.mkdirs()) {
            try {
                Files.write(path, make(configuration).getBytes());
            }
            catch (Exception e) {
                throw new RuntimeException("Could not write to path: " + path, e);
            }
        }
        else {
            throw new RuntimeException("could not create directory: " + parentDirectory.getAbsolutePath());
        }
    }
}
