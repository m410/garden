package org.m410.j8.module.ormbuilder.orm;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author m410
 */
public final class OneToMany  extends Node {
    private String name;
    private String mappedBy;
    private ORM.Cascade[] cascade = {};// should be enum
    private ORM.Fetch fetch = ORM.Fetch.LAZY; // should be enum
    private Class targetEntity = void.class;
    private boolean orphanRemoval = true;

    public OneToMany() {
        super(2,6);
    }

    public <T> OneToMany(String name, Class<T> targetEntity, String mappedBy) {
        this();
        this.name = name;
        this.targetEntity = targetEntity;
        this.mappedBy = mappedBy;
    }

    public <T> OneToMany(String name, Class<T> targetEntity) {
        this();
        this.name = name;
        this.targetEntity = targetEntity;
    }

    public OneToMany(String name, String mappedBy, ORM.Cascade[] cascade, ORM.Fetch fetch,
                     Class targetEntity, boolean orphanRemoval) {
        this();
        this.name = name;
        this.mappedBy = mappedBy;
        this.cascade = cascade;
        this.fetch = fetch;
        this.targetEntity = targetEntity;
        this.orphanRemoval = orphanRemoval;
    }

    @Override
    public void appendElement(Document root, Element parent) {
        Element id = root.createElement("one-to-many");
        id.setAttribute("name",name);
        id.setAttribute("mapped-by",mappedBy);
        id.setAttribute("target-entity",targetEntity.getName());
        children.stream().forEach(n->n.appendElement(root,id));
        parent.appendChild(id);
    }
}
