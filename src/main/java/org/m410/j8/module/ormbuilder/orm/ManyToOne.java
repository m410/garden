package org.m410.j8.module.ormbuilder.orm;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Arrays;


/**
 * @author m410
 */
public final class ManyToOne  extends Node {
    private String name; // property name
    private String mappedBy;
    private ORM.Cascade[] cascade = {};// should be enum
    private ORM.Fetch fetch = ORM.Fetch.EAGER; // should be enum
    private Class targetEntity = void.class;
    private boolean optional = true;

    //    private String access ; // should be enum
//    private boolean id = false;

    ManyToOne() {
        super(2,5);
    }

    public ManyToOne(String name, ORM.Cascade[] cascade, ORM.Fetch fetch, Class targetEntity, boolean optional) {
        this();
        this.name = name;
        this.cascade = cascade;
        this.fetch = fetch;
        this.targetEntity = targetEntity;
        this.optional = optional;
    }

    public ManyToOne(String name) {
        this();
        this.name = name;
    }

    public ManyToOne(String name, Class targetEntity,String mappedBy) {
        this();
        this.name = name;
        this.targetEntity = targetEntity;
        this.mappedBy = mappedBy;
    }

    public ManyToOne(String name, ORM.Cascade[] cascade, ORM.Fetch fetch) {
        this();
        this.name = name;
        this.cascade = cascade;
        this.fetch = fetch;
    }

    public ManyToOne fetch(ORM.Fetch fetch) {
        return new ManyToOne();
    }

    public ManyToOne cascade(ORM.Fetch fetch) {
        return new ManyToOne();
    }

    @Override
    public void appendElement(Document root, Element parent) {
        Element id = root.createElement("many-to-one");
        id.setAttribute("name",name);
        id.setAttribute("fetch",fetch.toString());
        id.setAttribute("optional",Boolean.toString(optional));
        id.setAttribute("cascade", Arrays.toString(cascade));
        children.stream().forEach(n->n.appendElement(root,id));
        parent.appendChild(id);
    }
}
