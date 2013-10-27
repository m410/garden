package org.m410.j8.persistence.orm;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import java.io.IOException;

import static org.m410.j8.persistence.orm.ORM.*;

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
            "xsi:schemaLocation=\"http://www.eclipse.org/eclipselink/xsds/persistence/orm http://www.eclipse.org/eclipselink/xsds/eclipselink_orm_2_1.xsd\" " +
            "xmlns=\"http://www.eclipse.org/eclipselink/xsds/persistence/orm\">" +
            "<persistence-unit-metadata>" +
            "<xml-mapping-metadata-complete/>" +
            "<exclude-default-mappings/>" +
            "</persistence-unit-metadata>" +
            "<entity class=\"org.m410.demo.Person\">" +
            "<table name=\"person\"/>" +
            "<attributes>" +
            "<id name=\"id\">" +
            "<generated-value generator=\"SEQUENCE\" strategy=\"person_seq\"/>" +
            "</id>" +
            "<version name=\"version\"/>" +
            "<basic name=\"name\">" +
            "<column column-definition=\"\" insertable=\"true\" length=\"255\" name=\"my_name\" nullable=\"true\" precision=\"0\" scale=\"0\" table=\"\" unique=\"false\" updatable=\"true\"/>" +
            "</basic>" +
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
