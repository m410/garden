package org.m410.garden.module.logback;

import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;
import org.m410.garden.module.ormbuilder.orm.ConfigFileBuilder;
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
 * Logging configuration
 *
 *  @author Michael Fortin
 */
public final class LogbackXmlBuilder implements ConfigFileBuilder {

    String make(ImmutableHierarchicalConfiguration configuration) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.newDocument();
        Element root = doc.createElement("configuration");
        doc.appendChild(root);


        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);


        Element appender = doc.createElement("appender");
        appender.setAttribute("name","STDOUT");
        appender.setAttribute("class","ch.qos.logback.core.ConsoleAppender");
        Element layout = doc.createElement("layout");
        layout.setAttribute("class","ch.qos.logback.classic.PatternLayout");
        Element pattern = doc.createElement("pattern");
        pattern.setTextContent("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n");
        layout.appendChild(pattern);
        appender.appendChild(layout);
        root.appendChild(appender);

        Element logger = doc.createElement("logger");
        logger.setAttribute("name","org.m410");
        logger.setAttribute("level","debug");
        root.appendChild(logger);

        Element rootLog = doc.createElement("root");
        Element appenderRef = doc.createElement("appender-ref");
        appenderRef.setAttribute("ref","STDOUT");
        rootLog.appendChild(appenderRef);
        rootLog.setAttribute("level","INFO");
        root.appendChild(rootLog);

        StringWriter s = new StringWriter();
        StreamResult result = new StreamResult(s);
        transformer.transform(source, result);

        return s.toString();
    }

    @Override
    public void writeToFile(Path path, ImmutableHierarchicalConfiguration configuration) {
        try {
            path.getParent().toFile().mkdirs();
            Files.write(path, make(configuration).getBytes());
        } catch (Exception e) {
            throw new RuntimeException("Could not write to path: " + path, e);
        }
    }
}
