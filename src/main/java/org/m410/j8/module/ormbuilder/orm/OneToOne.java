package org.m410.j8.module.ormbuilder.orm;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author m410
 */
public final class OneToOne extends Node {

    private String name;
    private Class targetEntity = void.class;
    private String mappedBy;
    private String access;
    private ORM.Fetch fetch = ORM.Fetch.LAZY;


    OneToOne() {
        super(2,7);

    }

    public <T> OneToOne(String name, Class<T> targetEntity, String mappedBy) {
        this();
        this.name = name;
        this.targetEntity = targetEntity;
        this.mappedBy = mappedBy;
    }

    public OneToOne(String name, Class targetEntity) {
        this();
        this.name = name;
        this.targetEntity = targetEntity;
    }

    @Override
    public void appendElement(Document root, Element parent) {
        Element id = root.createElement("one-to-one");
        id.setAttribute("name",name);
        id.setAttribute("target-entity",targetEntity.getName());
        id.setAttribute("mapped-by",mappedBy);
        children.stream().forEach(n->n.appendElement(root,id));
        parent.appendChild(id);
    }
}
