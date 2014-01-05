package org.m410.j8.module.ormbuilder.orm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.m410.j8.sample.FixturePerson;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import java.io.IOException;

import static org.m410.j8.module.ormbuilder.orm.ORM.*;
import static org.junit.Assert.*;

/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class OrmXmlBuilderTest {
    static String expected =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                    "<entity-mappings version=\"2.1\" " +
                    "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                    "xsi:schemaLocation=\"http://xmlns.jcp.org/xml/ns/persistence/orm " +
                    "http://xmlns.jcp.org/xml/ns/persistence/orm_2_1.xsd\" " +
                    "xmlns=\"http://xmlns.jcp.org/xml/ns/persistence/orm\">" +
                    "<description>Generated Persistence Mapping</description>" +
                    "<persistence-unit-metadata>" +
                    "<xml-mapping-metadata-complete/>" +
                    "</persistence-unit-metadata>" +
                    "<entity class=\"org.m410.j8.sample.FixturePerson\">" +
                    "<table name=\"person\"/>" +
                    "<attributes>" +
                    "<id name=\"id\">" +
                    "<generated-value generator=\"personSeq\" strategy=\"SEQUENCE\"/>" +
                    "<sequence-generator allocation-size=\"1\" initial-value=\"1\" name=\"personSeq\" sequence-name=\"person_seq\"/>" +
                    "</id>" +
                    "<basic name=\"email\">" +
                    "<column insertable=\"true\" length=\"255\" name=\"email\" nullable=\"true\" unique=\"false\" updatable=\"true\"/>" +
                    "</basic>" +
                    "<basic name=\"name\">" +
                    "<column insertable=\"true\" length=\"255\" name=\"my_name\" nullable=\"true\" unique=\"false\" updatable=\"true\"/>" +
                    "</basic>" +
                    "<version name=\"version\"/>" +
                    "</attributes>" +
                    "</entity>" +
                    "</entity-mappings>";

    @Test
    public void buildOrmXml() throws ParserConfigurationException, TransformerException, IOException, SAXException {
        Entity entity = entity(FixturePerson.class, "person")
                .id("id", generatedValue(Strategy.SEQUENCE,"personSeq"),sequenceGenerator("personSeq","person_seq"))
                .version("version")
                .basic("name", column("my_name"))
                .basic("email", column("email"))
                .make();
        String result = new OrmXmlBuilder().addEntity(entity).make();
        assertEquals(expected, result);
    }
}
