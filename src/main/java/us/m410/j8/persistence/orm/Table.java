package us.m410.j8.persistence.orm;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public final class Table  extends Node {
    private String name;

    Table(String name) {
        this.name = name;
    }

    @Override
    public void appendElement(Document root, Element parent) {
        Element table = root.createElement("table");
        table.setAttribute("name", name);
        children.stream().forEach(n->n.appendElement(root,table));
        parent.appendChild(table);
    }
}
