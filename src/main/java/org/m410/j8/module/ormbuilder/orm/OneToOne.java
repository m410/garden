package org.m410.j8.module.ormbuilder.orm;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * <pre>
 *
 *  &lt;xsd:complexType name="one-to-one"gt;
 *    &lt;xsd:annotationgt;
 *    &lt;xsd:documentationgt;
 *        @Target({METHOD, FIELD}) @Retention(RUNTIME) public @interface OneToOne {
 *        Class targetEntity() default void.class;
 *        CascadeType[] cascade() default {};
 *        FetchType fetch() default EAGER;
 *        boolean optional() default true;
 *        String mappedBy() default "";
 *        boolean orphanRemoval() default false; }
 *    &lt;/xsd:documentationgt;
 *    &lt;/xsd:annotationgt;
 *    &lt;xsd:sequencegt;
 *      &lt;xsd:choicegt;
 *        &lt;xsd:sequencegt;
 *          &lt;xsd:element name="primary-key-join-column" type="orm:primary-key-join-column" minOccurs="0" maxOccurs="unbounded" /gt;
 *          &lt;xsd:element name="primary-key-foreign-key" type="orm:foreign-key" minOccurs="0" /gt;
 *        &lt;/xsd:sequencegt;
 *        &lt;xsd:sequencegt;
 *          &lt;xsd:element name="join-column" type="orm:join-column" minOccurs="0" maxOccurs="unbounded" /gt;
 *          &lt;xsd:element name="foreign-key" type="orm:foreign-key" minOccurs="0" /gt;
 *        &lt;/xsd:sequencegt;
 *        &lt;xsd:element name="join-table" type="orm:join-table" minOccurs="0" /gt;
 *      &lt;/xsd:choicegt;
 *      &lt;xsd:element name="cascade" type="orm:cascade-type" minOccurs="0" /gt;
 *    &lt;/xsd:sequencegt;
 *    &lt;xsd:attribute name="name" type="xsd:string" use="required" /gt;
 *    &lt;xsd:attribute name="target-entity" type="xsd:string" /gt;
 *    &lt;xsd:attribute name="fetch" type="orm:fetch-type" /gt;
 *    &lt;xsd:attribute name="optional" type="xsd:boolean" /gt;
 *    &lt;xsd:attribute name="access" type="orm:access-type" /gt;
 *    &lt;xsd:attribute name="mapped-by" type="xsd:string" /gt;
 *    &lt;xsd:attribute name="orphan-removal" type="xsd:boolean" /gt;
 *    &lt;xsd:attribute name="maps-id" type="xsd:string" /gt;
 *    &lt;xsd:attribute name="id" type="xsd:boolean" /gt;
 *  &lt;/xsd:complexTypegt;
 *
 * </pre>
 * @author m410
 */
public final class OneToOne extends Node {

    private String name;
    private Class targetEntity = void.class;
    private String mappedBy = "";
    private ORM.Access access;
    private ORM.Fetch fetch = ORM.Fetch.EAGER;

    private boolean optional = true;
    private boolean orphanRemoval = false;
    private String mapsId;
    private String id;

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

        if(targetEntity != void.class)
            id.setAttribute("target-entity",targetEntity.getName());

        id.setAttribute("fetch",fetch.toString());
        id.setAttribute("optional",String.valueOf(optional));

        if(access != null)
            id.setAttribute("access",access.toString());

        if(!"".equals(mappedBy))
            id.setAttribute("mapped-by",mappedBy);

        id.setAttribute("orphan-removal",String.valueOf(orphanRemoval));

//        id.setAttribute("maps-id",mapsId);
//        id.setAttribute("id",id);

        // children
        //cascade, joinTable,joinColumn, primaryKey,foreignKey
        children.stream().forEach(n->n.appendElement(root,id));
        parent.appendChild(id);
    }
}
