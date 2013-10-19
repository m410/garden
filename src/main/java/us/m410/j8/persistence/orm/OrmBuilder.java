package us.m410.j8.persistence.orm;

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
import java.util.Arrays;

/**
 * //        <?xml version="1.0"?>
 * //        <entity-mappings
 * //        xmlns="http://www.eclipse.org/eclipselink/xsds/persistence/orm"
 * //        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 * //        xsi:schemaLocation="http://www.eclipse.org/eclipselink/xsds/persistence/orm http://www.eclipse.org/eclipselink/xsds/eclipselink_orm_2_1.xsd"
 * //        version="2.1">
 * //        <entity class="model.PhoneNumber">
 * //          <table name="PHONE" />
 * //          <attributes>
 * //            <id name="id">
 * //              <column name="ADDRESS_ID" />
 * //              <generated-value />
 * //            </id>
 * //            <basic name="areaCode">
 * //              <column name="AREA_CODE" />
 * //            </basic>
 * //            <basic name="number">
 * //              <column name="P_NUMBER" />
 * //            </basic>
 * //            <many-to-one name="owner">
 * //              <join-column name="EMP_ID" />
 * //            </many-to-one>
 * //          </attributes>
 * //        </entity>
 * //        </entity-mappings>
 *
 * @author Michael Fortin
 */
public final class OrmBuilder {

    private Node entityNode;
    private Node attributes = new Attributes();

    OrmBuilder(String ormClassName, String table) {
        this.entityNode = new Entity(ormClassName);
        this.entityNode.addChild(new Table(table));
        this.entityNode.addChild(attributes);
    }

    public OrmBuilder id(String id, Node... nodes) {
        final Id node = new Id(id);
        return append(node, nodes);
    }


    public OrmBuilder basic(String id, Node... nodes) {
        final Basic node = new Basic(id);
        return append(node, nodes);
    }

    public OrmBuilder version(String id, Node... nodes) {
        final Version node = new Version(id);
        return append(node, nodes);
    }

    public OrmBuilder manyToOne(String id, Node... nodes) {
        return this;
    }

    public OrmBuilder oneToMany(String id, Node... nodes) {
        return this;
    }

    public OrmBuilder manyToMany(String id, Node... nodes) {
        return this;
    }

    public OrmBuilder oneToOne(String id, Node... nodes) {
        return this;
    }

    private OrmBuilder append(Node node, Node[] children) {
        attributes.addChild(node);
        Arrays.asList(children).stream().forEach(node::addChild);
        return this;
    }

    /**
     * @see http://wiki.eclipse.org/EclipseLink/Examples/JPA/EclipseLink-ORM.XML
     * @see http://wiki.eclipse.org/EclipseLink/Examples/JPA/EmployeeXML
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
        final String nsUrl = "http://www.eclipse.org/eclipselink/xsds/persistence/orm";
        Element root = doc.createElementNS(nsUrl, "entity-mappings");
        root.setAttribute("version", "2.1");
        root.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance",
                "xsi:schemaLocation",
                "http://www.eclipse.org/eclipselink/xsds/persistence/orm " +
                        "http://www.eclipse.org/eclipselink/xsds/eclipselink_orm_2_1.xsd");
        doc.appendChild(root);

        Element meta = doc.createElement("persistence-unit-metadata");
        meta.appendChild(doc.createElement("xml-mapping-metadata-complete"));
        meta.appendChild(doc.createElement("exclude-default-mappings"));
        root.appendChild(meta);

        entityNode.appendElement(doc, root);

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
