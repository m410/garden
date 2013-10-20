package us.m410.j8.persistence.orm;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */

public final class Id  extends Node {
    private String name;

    Id(String name) {
        this.name = name;
    }

    @Override
    public void appendElement(Document root, Element parent) {
        Element id = root.createElement("id");
        id.setAttribute("name",name);
        children.stream().forEach(n->n.appendElement(root,id));
        parent.appendChild(id);
    }
}
