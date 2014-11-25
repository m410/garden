package org.m410.garden.module.ormbuilder.orm;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * <pre>
 *
 *   &lt;xsd:annotationgt;
 *     &lt;xsd:documentationgt;
 *       Target({METHOD, FIELD}) @Retention(RUNTIME) public @interface OneToMany {
 *          Class targetEntity() default void.class;
 *          CascadeType[] cascade() default {};
 *          FetchType fetch() default LAZY;
 *          String mappedBy() default ""; }
 *       &lt;/xsd:documentationgt;
 *     &lt;/xsd:annotationgt;
 *     &lt;xsd:sequencegt;
 *     &lt;xsd:choicegt;
 *     &lt;xsd:element name="order-by" type="orm:order-by" minOccurs="0" /gt;
 *     &lt;xsd:element name="order-column" type="orm:order-column" minOccurs="0" /gt;
 *     &lt;/xsd:choicegt;
 *     &lt;xsd:choicegt;
 *     &lt;xsd:element name="map-key" type="orm:map-key" minOccurs="0" /gt;
 *     &lt;xsd:sequencegt;
 *     &lt;xsd:element name="map-key-class" type="orm:map-key-class" minOccurs="0" /gt;
 *     &lt;xsd:choicegt;
 *     &lt;xsd:element name="map-key-temporal" type="orm:temporal" minOccurs="0" /gt;
 *     &lt;xsd:element name="map-key-enumerated" type="orm:enumerated" minOccurs="0" /gt;
 *     &lt;xsd:sequencegt;
 *     &lt;xsd:element name="map-key-attribute-override" type="orm:attribute-override" minOccurs="0" maxOccurs="unbounded" /gt;
 *     &lt;xsd:element name="map-key-convert" type="orm:convert" minOccurs="0" maxOccurs="unbounded" /gt;
 *     &lt;/xsd:sequencegt;
 *     &lt;/xsd:choicegt;
 *     &lt;xsd:choicegt;
 *     &lt;xsd:element name="map-key-column" type="orm:map-key-column" minOccurs="0" /gt;
 *     &lt;xsd:sequencegt;
 *     &lt;xsd:element name="map-key-join-column" type="orm:map-key-join-column" minOccurs="0" maxOccurs="unbounded" /gt;
 *     &lt;xsd:element name="map-key-foreign-key" type="orm:foreign-key" minOccurs="0" /gt;
 *     &lt;/xsd:sequencegt;
 *     &lt;/xsd:choicegt;
 *     &lt;/xsd:sequencegt;
 *     &lt;/xsd:choicegt;
 *     &lt;xsd:choicegt;
 *     &lt;xsd:element name="join-table" type="orm:join-table" minOccurs="0" /gt;
 *     &lt;xsd:sequencegt;
 *     &lt;xsd:element name="join-column" type="orm:join-column" minOccurs="0" maxOccurs="unbounded" /gt;
 *     &lt;xsd:element name="foreign-key" type="orm:foreign-key" minOccurs="0" /gt;
 *     &lt;/xsd:sequencegt;
 *     &lt;/xsd:choicegt;
 *     &lt;xsd:element name="cascade" type="orm:cascade-type" minOccurs="0" /gt;
 *     &lt;/xsd:sequencegt;
 *     &lt;xsd:attribute name="name" type="xsd:string" use="required" /gt;
 *     &lt;xsd:attribute name="target-entity" type="xsd:string" /gt;
 *     &lt;xsd:attribute name="fetch" type="orm:fetch-type" /gt;
 *     &lt;xsd:attribute name="access" type="orm:access-type" /gt;
 *     &lt;xsd:attribute name="mapped-by" type="xsd:string" /gt;
 *   &lt;xsd:attribute name="orphan-removal" type="xsd:boolean" /gt;
 *
 * </pre>
 *
 * @author m410
 */
public final class OneToMany extends Node {
    private String name;
    private String mappedBy = "";
    private ORM.Cascade[] cascade = {};// should be enum
    private ORM.Fetch fetch = ORM.Fetch.LAZY; // should be enum
    private Class targetEntity = void.class;
    private boolean orphanRemoval = true;
    private ORM.Access access = null;

    public OneToMany() {
        super(2, 6);
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
        id.setAttribute("name", name);

        if(targetEntity != void.class)
            id.setAttribute("target-entity", targetEntity.getName());

        id.setAttribute("fetch", fetch.toString());

        if (access != null)
            id.setAttribute("access", access.toString());

        if(!"".equals(mappedBy))
            id.setAttribute("mapped-by", mappedBy);

        id.setAttribute("orphan-removal", String.valueOf(orphanRemoval));

        // children
        // foreignKey,joinTable,joinColumn,orderBy,orderColumn, etc
        children.stream().forEach(n -> n.appendElement(root, id));
        parent.appendChild(id);
    }
}
