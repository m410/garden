package org.m410.j8.module.ormbuilder.orm;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * An orm.xml node.
 *
 * Strategy can be one of, [TABLE, SEQUENCE, IDENTITY, AUTO].
 *
 * @author Michael Fortin
 */
public final class GeneratedValue extends Node {
    ORM.Strategy strategy;
    String generator;


    GeneratedValue(ORM.Strategy strategy, String generator) {
        super(3,1);
        this.strategy = strategy;
        this.generator = generator;
    }

    @Override
    public void appendElement(Document root, Element parent) {
        Element basic = root.createElement("generated-value");
        basic.setAttribute("strategy",strategy.toString());
        basic.setAttribute("generator",generator);
        children.stream().forEach(n->n.appendElement(root,basic));
        parent.appendChild(basic);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(3,3)
                .append(strategy)
                .append(generator)
                .hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GeneratedValue)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        GeneratedValue rhs = (GeneratedValue) obj;
        return new EqualsBuilder()
                .append(this.strategy, rhs.strategy)
                .append(this.generator, rhs.generator)
                .isEquals();
    }
}
