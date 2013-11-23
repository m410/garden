package org.m410.j8.module.ormbuiler.orm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import java.io.IOException;

import static org.m410.j8.module.ormbuiler.orm.ORM.*;
import static org.junit.Assert.*;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class OrmBuilderTest {
    static String expected =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                    "<entity-mappings version=\"2.1\" " +
                    "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                    "xsi:schemaLocation=\"http://xmlns.jcp.org/xml/ns/persistence/orm http://xmlns.jcp.org/xml/ns/persistence/orm_2_1.xsd\" " +
                    "xmlns=\"http://xmlns.jcp.org/xml/ns/persistence/orm\">" +
                    "<description>Description goes here</description>" +
                    "<persistence-unit-metadata>" +
                    "<xml-mapping-metadata-complete/>" +
                    "</persistence-unit-metadata>" +
                    "<entity class=\"org.m410.demo.Person\">" +
                    "<table name=\"person\"/>" +
                    "<attributes>" +
                    "<id name=\"id\">" +
                    "<generated-value generator=\"SEQUENCE\" strategy=\"person_seq\"/>" +
                    "</id>" +
                    "<basic name=\"name\"><column insertable=\"true\" length=\"255\" name=\"my_name\" nullable=\"true\" unique=\"false\" updatable=\"true\"/>" +
                    "</basic>" +
                    "<version name=\"version\"/>" +
                    "</attributes>" +
                    "</entity>" +
                    "</entity-mappings>";

    @Test
    public void buildOrmXml() throws ParserConfigurationException, TransformerException, IOException, SAXException {
        Entity entity = entity("org.m410.demo.Person", "person")
                .id("id", generatedValue("person_seq", "SEQUENCE"))
                .version("version")
                .basic("name", column("my_name"))
                .make();
        String result = new OrmXmlBuilder().addEntity(entity).make();
        assertEquals(expected, result);
    }
}
