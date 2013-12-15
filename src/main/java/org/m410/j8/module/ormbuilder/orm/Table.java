package org.m410.j8.module.ormbuilder.orm;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * An orm.xml node.
 * A table node on an entity.
 *
 * @author Michael Fortin
 */
public final class Table  extends Node {
    String name;

    Table(String name) {
        super(1,0);
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
