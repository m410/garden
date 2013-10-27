package org.m410.j8.persistence.unit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class PersistenceUnitBuilderTest {
    static String expected =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                    "<persistence version=\"1.0\" " +
                    "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                    "xsi:schemaLocation=\"http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_1_0.xsd\" " +
                    "xmlns=\"http://java.sun.com/xml/ns/persistence\">" +
                    "<persistence-unit name=\"demo\" transaction-type=\"RESOURCE_LOCAL\">" +
                    "<description>Sample persistence mapping</description>" +
                    "<mapping-file>org/m410/demo/orm.xml</mapping-file>" +
                    "<properties>" +
                    "<property name=\"sampleName\" value=\"sampleValue\"/>" +
                    "</properties>" +
                    "</persistence-unit>" +
                    "</persistence>";

    @Test
    public void buildOrmXml() throws ParserConfigurationException, TransformerException, IOException, SAXException {
        String result = new PersistenceUnitBuilder()
                .name("demo")
                .description("Sample persistence mapping")
                .mappingFile("org.m410.demo")
                .transactionType("RESOURCE_LOCAL")
                .property("sampleName","sampleValue")
                .make();
        assertEquals(expected, result);
    }
}
