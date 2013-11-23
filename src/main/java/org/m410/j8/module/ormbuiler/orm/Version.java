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
public final class Version  extends Node {
    protected int listOrder = 120;
    private String name;

    Version(String name) {
        super(3);
        this.name = name;
    }

    @Override
    public void appendElement(Document root, Element parent) {
        Element version = root.createElement("version");
        version.setAttribute("name", name);
        children.stream().forEach(n->n.appendElement(root,version));
        parent.appendChild(version);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(13,13)
                .append(name)
                .hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Version)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Version rhs = (Version) obj;
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
