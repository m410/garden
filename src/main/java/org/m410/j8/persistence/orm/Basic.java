package org.m410.j8.persistence.orm;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public final class Basic  extends Node {
    private String name;

    Basic(String name) {
        super(2);
        this.name = name;
    }

    @Override
    public void appendElement(Document root, Element parent) {
        Element basic = root.createElement("basic");
        basic.setAttribute("name",name);
        children.stream().forEach(n->n.appendElement(root,basic));
        parent.appendChild(basic);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(3,7)
                .append(name)
                .hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Basic)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Basic rhs = (Basic) obj;
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
