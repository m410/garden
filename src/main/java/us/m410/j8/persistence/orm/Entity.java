package us.m410.j8.persistence.orm;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
final class Entity  extends Node {
    private String className;

    public Entity(String className) {
        this.className = className;
    }

    @Override
    public void appendElement(Document root, Element parent) {
        Element entity = root.createElement("entity");
        entity.setAttribute("class",className);
        children.stream().forEach(n->n.appendElement(root,entity));
        parent.appendChild(entity);
    }
}
