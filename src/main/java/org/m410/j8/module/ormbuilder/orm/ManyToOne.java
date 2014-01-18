package org.m410.j8.module.ormbuilder.orm;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Arrays;


/**
 *
 * <pre>
 *   &lt;xsd:complexType name="many-to-one"&gt;
 *    &lt;xsd:annotation&gt;
 *    &lt;xsd:documentation&gt;
 *        @Target({METHOD, FIELD}) @Retention(RUNTIME) public @interface ManyToOne {
 *          Class targetEntity() default void.class;
 *          CascadeType[] cascade() default {};
 *          FetchType fetch() default EAGER;
 *          boolean optional() default true;
 *          }
 *      &lt;/xsd:documentation&gt;
 *    &lt;/xsd:annotation&gt;
 *    &lt;xsd:sequence&gt;
 *    &lt;xsd:choice&gt;
 *      &lt;xsd:sequence&gt;
 *        &lt;xsd:element name="join-column" type="orm:join-column" minOccurs="0" maxOccurs="unbounded" /&gt;
 *        &lt;xsd:element name="foreign-key" type="orm:foreign-key" minOccurs="0" /&gt;
 *      &lt;/xsd:sequence&gt;
 *      &lt;xsd:element name="join-table" type="orm:join-table" minOccurs="0" /&gt;
 *    &lt;/xsd:choice&gt;
 *    &lt;xsd:element name="cascade" type="orm:cascade-type" minOccurs="0" /&gt;
 *    &lt;/xsd:sequence&gt;
 *    &lt;xsd:attribute name="name" type="xsd:string" use="required" /&gt;
 *    &lt;xsd:attribute name="target-entity" type="xsd:string" /&gt;
 *    &lt;xsd:attribute name="fetch" type="orm:fetch-type" /&gt;
 *    &lt;xsd:attribute name="optional" type="xsd:boolean" /&gt;
 *    &lt;xsd:attribute name="access" type="orm:access-type" /&gt;
 *    &lt;xsd:attribute name="maps-id" type="xsd:string" /&gt;
 *    &lt;xsd:attribute name="id" type="xsd:boolean" /&gt;
 *   &lt;/xsd:complexType&gt;
 * </pre>
 *
 * @author m410
 */
public final class ManyToOne  extends Node {
    private String name;
    private String mappedBy;
    private ORM.Cascade[] cascade = {};
    private ORM.Fetch fetch = ORM.Fetch.EAGER;
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

        if(targetEntity != void.class)
            id.setAttribute("target-entity",targetEntity.getName());

        id.setAttribute("fetch",fetch.toString());
        id.setAttribute("optional",Boolean.toString(optional));
        // access
        // maps-id
        // id

        //children
        // joinColumn, foreignKey, cascade
        children.stream().forEach(n -> n.appendElement(root, id));
        parent.appendChild(id);
    }
}
