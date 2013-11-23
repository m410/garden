package org.m410.j8.module.ormbuiler.orm;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * TABLE, SEQUENCE, IDENTITY, AUTO
 *
 * @author Michael Fortin
 */
public final class SequenceGenerator extends Node {
    String name;
    String sequenceName;

    SequenceGenerator(String name, String sequenceName) {
        super(3);
        this.name = name;
        this.sequenceName = sequenceName;
    }

    @Override
    public void appendElement(Document root, Element parent) {
        Element basic = root.createElement("sequence-generator");
        basic.setAttribute("name", name);
        basic.setAttribute("sequence-name", sequenceName);
        children.stream().forEach(n->n.appendElement(root,basic));
        parent.appendChild(basic);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(3,3)
                .append(name)
                .append(sequenceName)
                .hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SequenceGenerator)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        SequenceGenerator rhs = (SequenceGenerator) obj;
        return new EqualsBuilder()
                .append(this.name, rhs.name)
                .append(this.sequenceName, rhs.sequenceName)
                .isEquals();
    }
}
