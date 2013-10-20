package us.m410.j8.persistence.orm;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * TABLE, SEQUENCE, IDENTITY, AUTO
 *
 * @author Michael Fortin
 */
public final class GeneratedValue extends Node {
    String strategy;
    String generator;

    GeneratedValue(String strategy, String generator) {
        this.strategy = strategy;
        this.generator = generator;
    }

    @Override
    public void appendElement(Document root, Element parent) {
        Element basic = root.createElement("generated-value");
        basic.setAttribute("strategy",strategy);
        basic.setAttribute("generator",generator);
        children.stream().forEach(n->n.appendElement(root,basic));
        parent.appendChild(basic);
    }
}
