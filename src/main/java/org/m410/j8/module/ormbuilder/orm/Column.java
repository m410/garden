package org.m410.j8.module.ormbuilder.orm;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * An orm.xml node.
 *
 * @author Michael Fortin
 */
public final class Column extends Node {
    String name =  null;
    boolean unique =  false;
    boolean nullable =  true;
    boolean insertable =  true;
    boolean updatable =  true;
    String columnDefinition = null;
    String table =  null;
    int length =  255;
    int precision =  0; // decimal precision
    int scale =  0; // decimal scale

    Column(String name) {
        super(3,0);
        this.name = name;
    }

    Column(String name, int length, boolean nullable) {
        this(name);
        this.length = length;
        this.nullable = nullable;
    }

    Column(String name, int length, boolean nullable, boolean unique) {
        this(name);
        this.length = length;
        this.nullable = nullable;
        this.unique= unique;
    }

    Column(String name, boolean unique, boolean nullable, boolean insertable, boolean updatable,
            String columnDefinition, String table, int length, int precision, int scale) {
        this(name);
        this.unique = unique;
        this.nullable = nullable;
        this.insertable = insertable;
        this.updatable = updatable;
        this.columnDefinition = columnDefinition;
        this.table = table;
        this.length = length;
        this.precision = precision;
        this.scale = scale;
    }

    public Column length(int length) {
        return new Column(name,unique,nullable,insertable,updatable,
                columnDefinition,table,length,precision,scale);
    }

    public Column precision(int precision) {
        return new Column(name,unique,nullable,insertable,updatable,
                columnDefinition,table,length,precision,scale);
    }

    public Column scale(int scale) {
        return new Column(name,unique,nullable,insertable,updatable,
                columnDefinition,table,length,precision,scale);
    }

    public Column table(String table) {
        return new Column(name,unique,nullable,insertable,updatable,
                columnDefinition,table,length,precision,scale);
    }

    public Column definition(String columnDefinition) {
        return new Column(name,unique,nullable,insertable,updatable,
                columnDefinition,table,length,precision,scale);
    }

    public Column notNull() {
        return new Column(name,unique,false,insertable,updatable,
                columnDefinition,table,length,precision,scale);
    }

    public Column unique() {
        return new Column(name,true,nullable,insertable,updatable,
                columnDefinition,table,length,precision,scale);
    }

    @Override
    public void appendElement(Document root, Element parent) {
        Element basic = root.createElement("column");
        basic.setAttribute("name",name);
        basic.setAttribute("unique",String.valueOf(unique));
        basic.setAttribute("nullable",String.valueOf(nullable));
        basic.setAttribute("insertable",String.valueOf(insertable));
        basic.setAttribute("updatable",String.valueOf(updatable));

        if(columnDefinition != null)
            basic.setAttribute("column-definition",columnDefinition);

        if(table != null)
            basic.setAttribute("table",table);

        basic.setAttribute("length",String.valueOf(length));

        if(precision>0 )
            basic.setAttribute("precision",String.valueOf(precision));
        if(scale>0)
            basic.setAttribute("scale",String.valueOf(scale));

        children.stream().forEach(n->n.appendElement(root,basic));
        parent.appendChild(basic);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(3,11)
                .append(name)
                .hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Column)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Column rhs = (Column) obj;
        return new EqualsBuilder()
                .append(this.name, rhs.name)
                .isEquals();
    }
}
