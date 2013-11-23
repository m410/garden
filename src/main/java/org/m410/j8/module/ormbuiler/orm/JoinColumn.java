package org.m410.j8.module.ormbuiler.orm;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * An orm.xml node.
 *
 * @author Michael Fortin
 */
public final class JoinColumn extends Node {
    String name =  "";
    String referencedColumnName =  "";
    boolean unique =  false;
    boolean nullable =  true;
    boolean insertable =  true;
    boolean updatable =  true;
    String columnDefinition =  "";
    String table =  "";

    JoinColumn(String name, String referencedColumnName, String table) {
        super(0);
        this.name = name;
        this.referencedColumnName = referencedColumnName;
        this.table = table;
    }

    JoinColumn(String name, String referencedColumnName, boolean unique, String table) {
        this(name,referencedColumnName,table);
        this.unique = unique;
    }

    JoinColumn(String name, String referencedColumnName, boolean unique, boolean nullable,
            boolean insertable, boolean updatable, String columnDefinition, String table) {
        this(name,referencedColumnName,table);
        this.unique = unique;
        this.nullable = nullable;
        this.insertable = insertable;
        this.updatable = updatable;
        this.columnDefinition = columnDefinition;
    }

    @Override
    public void appendElement(Document root, Element parent) {
        Element basic = root.createElement("join-column");
        basic.setAttribute("name",name);
        basic.setAttribute("referencedColumnName",referencedColumnName);
        basic.setAttribute("unique",String.valueOf(referencedColumnName));
        basic.setAttribute("nullable",String.valueOf(nullable));
        basic.setAttribute("insertable",String.valueOf(insertable));
        basic.setAttribute("updatable",String.valueOf(updatable));
        basic.setAttribute("column-definition",columnDefinition);
        basic.setAttribute("table",table);
        children.stream().forEach(n->n.appendElement(root,basic));
        parent.appendChild(basic);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(1,3)
                .append(name)
                .append(table)
                .hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof JoinColumn)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        JoinColumn rhs = (JoinColumn) obj;
        return new EqualsBuilder()
                .append(this.name, rhs.name)
                .append(this.table, rhs.table)
                .isEquals();
    }
}
