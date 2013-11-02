package org.m410.j8.persistence.orm;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
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
        super(0);
        this.name = name;
    }

    @Override
    public void appendElement(Document root, Element parent) {
        Element table = root.createElement("table");
        table.setAttribute("name", name);
        children.stream().forEach(n->n.appendElement(root,table));
        parent.appendChild(table);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(3,13)
                .append(name)
                .hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Table)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Table rhs = (Table) obj;
        return new EqualsBuilder()
                .append(this.name, rhs.name)
                .isEquals();
    }
}
