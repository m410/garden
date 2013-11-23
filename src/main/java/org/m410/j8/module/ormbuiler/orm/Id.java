package org.m410.j8.module.ormbuiler.orm;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * An orm.xml node.
 *
 * @author Michael Fortin
 */
public final class Id  extends Node {
    private String name;

    Id(String name) {
        super(0);
        this.name = name;
    }

    @Override
    public void appendElement(Document root, Element parent) {
        Element id = root.createElement("id");
        id.setAttribute("name",name);
        children.stream().forEach(n->n.appendElement(root,id));
        parent.appendChild(id);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(3,3)
                .append(name)
                .hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Id)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Id rhs = (Id) obj;
        return new EqualsBuilder()
                .append(this.name, rhs.name)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name",name)
                .append("ord",listOrder)
                .toString();
    }
}
