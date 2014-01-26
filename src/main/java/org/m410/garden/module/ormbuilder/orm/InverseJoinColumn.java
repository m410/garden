package org.m410.garden.module.ormbuilder.orm;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.m410.garden.module.ormbuilder.orm.ORM.Fetch;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * An orm.xml node.
 *
 * @author Michael Fortin
 */
public final class InverseJoinColumn extends Node {

    // todo should probably extends JoinColumn

    private String name =  "";
    private String referencedColumnName =  "";
    private boolean unique =  false;
    private boolean nullable =  true;
    private boolean insertable =  true;
    private boolean updatable =  true;
    private String columnDefinition =  "";
    private String table =  "";
    private Fetch fetch = Fetch.LAZY;

    public InverseJoinColumn(String name, String referencedColumnName, String table) {
        super(3,2);
        this.name = name;
        this.referencedColumnName = referencedColumnName;
        this.table = table;
    }

    public InverseJoinColumn(String name, String referencedColumnName, boolean unique, String table) {
        this(name,referencedColumnName,table);
        this.unique = unique;
    }

    public InverseJoinColumn(String name, String referencedColumnName, boolean unique, boolean nullable,
                             boolean insertable, boolean updatable, String columnDefinition, String table, Fetch fetch) {
        this(name,referencedColumnName,table);
        this.unique = unique;
        this.nullable = nullable;
        this.insertable = insertable;
        this.updatable = updatable;
        this.columnDefinition = columnDefinition;
        this.fetch = fetch;
    }

    public InverseJoinColumn(String name) {
        this(name,null,null);
    }

    public InverseJoinColumn fetch(ORM.Fetch fetch) {
        return new InverseJoinColumn(name,referencedColumnName,unique,nullable,insertable,
                updatable,columnDefinition,table, fetch);
    }

    public InverseJoinColumn unique() {
        return new InverseJoinColumn(name,referencedColumnName,true,nullable,insertable,
                updatable,columnDefinition,table, fetch);
    }

    public InverseJoinColumn definition(String columnDefinition) {
        return new InverseJoinColumn(name,referencedColumnName,unique,nullable,insertable,
                updatable,columnDefinition,table, fetch);
    }

    public InverseJoinColumn notNull() {
        return new InverseJoinColumn(name,referencedColumnName,unique,false,insertable,
                updatable,columnDefinition,table, fetch);
    }


    @Override
    public void appendElement(Document root, Element parent) {
        Element basic = root.createElement("inverse-join-column");
        basic.setAttribute("name",name);

        if(!"".equals(referencedColumnName))
            basic.setAttribute("referencedColumnName",referencedColumnName);

        basic.setAttribute("unique",String.valueOf(unique));
        basic.setAttribute("nullable",String.valueOf(nullable));
        basic.setAttribute("insertable",String.valueOf(insertable));
        basic.setAttribute("updatable",String.valueOf(updatable));

        if(!"".equals(columnDefinition))
            basic.setAttribute("column-definition",columnDefinition);

        if(!"".equals(table))
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
        if (!(obj instanceof InverseJoinColumn)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        InverseJoinColumn rhs = (InverseJoinColumn) obj;
        return new EqualsBuilder()
                .append(this.name, rhs.name)
                .append(this.table, rhs.table)
                .isEquals();
    }
}
