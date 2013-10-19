package us.m410.j8.persistence.orm;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
final class Column extends Node {
    String name =  "";
    boolean unique =  false;
    boolean nullable =  true;
    boolean insertable =  true;
    boolean updatable =  true;
    String columnDefinition =  "";
    String table =  "";
    int length =  255;
    int precision =  0; // decimal precision
    int scale =  0; // decimal scale

    Column(String name) {
        this.name = name;
    }

    Column(String name, int length, boolean nullable) {
        this.name = name;
        this.length = length;
        this.nullable = nullable;
    }

    Column(String name, int length, boolean nullable, boolean unique) {
        this.name = name;
        this.length = length;
        this.nullable = nullable;
        this.unique= unique;
    }

    Column(String name, boolean unique, boolean nullable, boolean insertable, boolean updatable,
            String columnDefinition, String table, int length, int precision, int scale) {
        this.name = name;
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


    @Override
    public void appendElement(Document root, Element parent) {
        Element basic = root.createElement("column");
        basic.setAttribute("name",name);
        basic.setAttribute("unique",String.valueOf(unique));
        basic.setAttribute("nullable",String.valueOf(nullable));
        basic.setAttribute("insertable",String.valueOf(insertable));
        basic.setAttribute("updatable",String.valueOf(updatable));
        basic.setAttribute("column-definition",columnDefinition);
        basic.setAttribute("table",table);
        basic.setAttribute("length",String.valueOf(length));
        basic.setAttribute("precision",String.valueOf(precision));
        basic.setAttribute("scale",String.valueOf(scale));
        children.stream().forEach(n->n.appendElement(root,basic));
        parent.appendChild(basic);
    }
}
