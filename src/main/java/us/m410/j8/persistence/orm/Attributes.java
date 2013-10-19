package us.m410.j8.persistence.orm;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
final class Attributes extends Node {
    @Override
    public void appendElement(Document root, Element parent) {
        Element attributes = root.createElement("attributes");
        children.stream().forEach(n->n.appendElement(root,attributes));
        parent.appendChild(attributes);
    }
}
