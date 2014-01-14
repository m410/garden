package org.m410.j8.module.ormbuilder.orm;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.m410.j8.module.ormbuilder.orm.ORM.Fetch;
/**
 * An orm.xml node.
 *
 * @author Michael Fortin
 */
public final class JoinColumn extends Node {
    private String name =  "";
    private String referencedColumnName =  "";
    private boolean unique =  false;
    private boolean nullable =  true;
    private boolean insertable =  true;
    private boolean updatable =  true;
    private String columnDefinition =  "";
    private String table =  "";
    private Fetch fetch = Fetch.LAZY;

    public JoinColumn(String name, String referencedColumnName, String table) {
        super(3,1);
        this.name = name;
        this.referencedColumnName = referencedColumnName;
        this.table = table;
    }

    public JoinColumn(String name, String referencedColumnName, boolean unique, String table) {
        this(name,referencedColumnName,table);
        this.unique = unique;
    }

    public JoinColumn(String name, String referencedColumnName, boolean unique, boolean nullable,
            boolean insertable, boolean updatable, String columnDefinition, String table, Fetch fetch) {
        this(name,referencedColumnName,table);
        this.unique = unique;
        this.nullable = nullable;
        this.insertable = insertable;
        this.updatable = updatable;
        this.columnDefinition = columnDefinition;
        this.fetch = fetch;
    }

    public JoinColumn(String name) {
        this(name,null,null);
    }

    public JoinColumn fetch(ORM.Fetch fetch) {
        return new JoinColumn(name,referencedColumnName,true,nullable,insertable,
                updatable,columnDefinition,table, fetch);
    }

    public JoinColumn unique() {
        return new JoinColumn(name,referencedColumnName,true,nullable,insertable,
                updatable,columnDefinition,table, fetch);
    }

    public JoinColumn definition(String columnDefinition) {
        return new JoinColumn(name,referencedColumnName,unique,nullable,insertable,
                updatable,columnDefinition,table, fetch);
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
