package org.m410.j8.module.ormbuilder.orm;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Arrays;

/**
 * @author m410
 */
public final class ManyToMany extends Node {

    private String name;
    private Class targetEntity = void.class;
    private String mappedBy;
    private String access;
    private ORM.Fetch fetch = ORM.Fetch.LAZY;


    ManyToMany() {
        super(3, 1);

    }

    public <T> ManyToMany(String name, Class<T> targetEntity, String mappedBy) {
        this();
        this.name = name;
        this.targetEntity = targetEntity;
        this.mappedBy = mappedBy;
    }

    @Override
    public void appendElement(Document root, Element parent) {
        Element id = root.createElement("many-to-many");
        id.setAttribute("name",name);
        id.setAttribute("target-entity",targetEntity.getName());
        id.setAttribute("mapped-by",mappedBy);
        children.stream().forEach(n->n.appendElement(root,id));
        parent.appendChild(id);
    }
}
