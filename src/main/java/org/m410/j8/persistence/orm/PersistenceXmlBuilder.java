package org.m410.j8.persistence.orm;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.HashMap;
import java.util.Map;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class PersistenceXmlBuilder {
    private Map<String,String> properties = new HashMap<>();

    public void make() throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.newDocument();
        final String nsUrl = "http://xmlns.jcp.org/xml/ns/persistence";
        Element root = doc.createElementNS(nsUrl, "entity-mappings");
        root.setAttribute("version", "2.1");
        root.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance",
                "xsi:schemaLocation",
                "http://java.sun.com/xml/ns/persistence " +
                        "http://java.sun.com/xml/ns/persistence/persistence_2_1.xsd");
        doc.appendChild(root);

        Element persistUnit = doc.createElement("persistence-unit");
        persistUnit.setAttribute("name","FIX_ME");
        persistUnit.setAttribute("transaction-type","RESOURCE_LOCAL");

        Element provider = doc.createElement("provider");
        provider.setTextContent("org.hibernate.ejb.HibernatePersistence");
        persistUnit.appendChild(provider);

        Element mappingFile = doc.createElement("mapping-file");
        provider.setTextContent("/META-INF/orm.xml");
        persistUnit.appendChild(mappingFile);

        Element propertiesElem = doc.createElement("properties");
        properties.forEach((k,v)->{
            Element propElem = doc.createElement("property");
            propElem.setAttribute("name",k);
            propElem.setAttribute("value",v);
            propertiesElem.appendChild(propElem);
        });
        persistUnit.appendChild(propertiesElem);

        root.appendChild(persistUnit);
    }
}
