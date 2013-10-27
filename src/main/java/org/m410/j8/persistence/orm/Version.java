package org.m410.j8.persistence.orm;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public final class Version  extends Node {
    private String name;

    Version(String name) {
        this.name = name;
    }

    @Override
    public void appendElement(Document root, Element parent) {
        Element version = root.createElement("version");
        version.setAttribute("name", name);
        children.stream().forEach(n->n.appendElement(root,version));
        parent.appendChild(version);
    }
}
