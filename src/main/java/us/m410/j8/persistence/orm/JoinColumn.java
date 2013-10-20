package us.m410.j8.persistence.orm;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Document Me..
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
        this.name = name;
        this.referencedColumnName = referencedColumnName;
        this.table = table;
    }

    JoinColumn(String name, String referencedColumnName, boolean unique, String table) {
        this.name = name;
        this.referencedColumnName = referencedColumnName;
        this.unique = unique;
        this.table = table;
    }

    JoinColumn(String name, String referencedColumnName, boolean unique, boolean nullable,
            boolean insertable, boolean updatable, String columnDefinition, String table) {
        this.name = name;
        this.referencedColumnName = referencedColumnName;
        this.unique = unique;
        this.nullable = nullable;
        this.insertable = insertable;
        this.updatable = updatable;
        this.columnDefinition = columnDefinition;
        this.table = table;
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
}
