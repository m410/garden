package org.m410.garden.module.ormbuilder.orm;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * An orm.xml node.
 *
 * @author Michael Fortin
 */
public final class Attributes extends Node {


    Attributes() {
        super(1,1);
    }

    @Override
    public void appendElement(Document root, Element parent) {
        Element attributes = root.createElement("attributes");
        children.stream().forEach(n->n.appendElement(root,attributes));
        parent.appendChild(attributes);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(3,5)
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
                .isEquals();
    }
}
