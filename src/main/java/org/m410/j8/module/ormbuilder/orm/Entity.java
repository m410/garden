package org.m410.j8.module.ormbuilder.orm;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * An orm.xml node.  This is the root node to created by the entityFactory.
 *
 * @author Michael Fortin
 */
public final class Entity  extends Node {
    String className;

    Entity(String className) {
        super(0,0);
        this.className = className;
    }

    @Override
    public void appendElement(Document root, Element parent) {
        Element entity = root.createElement("entity");
        entity.setAttribute("class",className);
        children.stream().forEach(n->n.appendElement(root,entity));
        parent.appendChild(entity);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(3,3)
                .append(className)
                .hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Entity)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Entity rhs = (Entity) obj;
        return new EqualsBuilder()
                .append(this.className, rhs.className)
                .isEquals();
    }
}
