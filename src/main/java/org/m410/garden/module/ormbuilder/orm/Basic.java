package org.m410.garden.module.ormbuilder.orm;

import org.apache.commons.lang3.builder.CompareToBuilder;
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
public final class Basic  extends Node {
    String name;

    Basic(String name) {
        super(2,1);
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
                .append("ord", nodeOrder)
                .toString();
    }

    @Override
    public int compareTo(Node o) {
        final CompareToBuilder compareToBuilder = new CompareToBuilder()
                .appendSuper(super.compareTo(o));

        if(o instanceof Basic)
            compareToBuilder.append(this.name, ((Basic) o).name);

        return compareToBuilder.toComparison();
    }
}
