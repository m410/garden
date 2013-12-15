package org.m410.j8.module.ormbuilder.orm;

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
    String schema;
    String description;
    int initialValue=1;
    int allocationSize=1;

    SequenceGenerator(String name, String sequenceName) {
        super(3,2);
        this.name = name;
        this.sequenceName = sequenceName;
    }

    SequenceGenerator(String name, String sequenceName,String schema, String description) {
        super(3,3);
        this.name = name;
        this.sequenceName = sequenceName;
        this.description = description;
        this.schema = schema;
    }

    SequenceGenerator(String name, String sequenceName, int initialValue,int allocationSize) {
        super(3,3);
        this.name = name;
        this.sequenceName = sequenceName;
        this.initialValue = initialValue;
        this.allocationSize = allocationSize;
    }

    @Override
    public void appendElement(Document root, Element parent) {
        Element basic = root.createElement("sequence-generator");
        basic.setAttribute("name", name);
        basic.setAttribute("sequence-name", sequenceName);
        basic.setAttribute("allocation-size", Integer.valueOf(allocationSize).toString());
        basic.setAttribute("initial-value", Integer.valueOf(initialValue).toString());

        if(description != null)
            basic.setAttribute("description", description);

        if(schema != null)
            basic.setAttribute("schema", schema);

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
