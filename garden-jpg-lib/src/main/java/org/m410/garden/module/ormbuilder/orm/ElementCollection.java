package org.m410.garden.module.ormbuilder.orm;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * An orm.xml node.
 *
 * <pre>
 *
 *  &lt;xsd:complexType name="element-collection"&gt;
 *  &lt;xsd:annotation&gt;
 *  &lt;xsd:documentation&gt;
 *  Target({METHOD, FIELD}) @Retention(RUNTIME)
 *      public @interface ElementCollection {
 *      Class targetClass() default void.class;
 *      FetchType fetch() default LAZY;
 *  }
 *  &lt;/xsd:documentation&gt;
 *  &lt;/xsd:annotation&gt;
 *  &lt;xsd:sequence&gt;
 *  &lt;xsd:choice&gt;
 *  &lt;xsd:element name="order-by" type="orm:order-by" minOccurs="0"/&gt;
 *  &lt;xsd:element name="order-column" type="orm:order-column" minOccurs="0"/&gt;
 *  &lt;/xsd:choice&gt;
 *  &lt;xsd:choice&gt;
 *  &lt;xsd:element name="map-key" type="orm:map-key" minOccurs="0"/&gt;
 *  &lt;xsd:sequence&gt;
 *  &lt;xsd:element name="map-key-class" type="orm:map-key-class" minOccurs="0"/&gt;
 *  &lt;xsd:choice&gt;
 *  &lt;xsd:element name="map-key-temporal" type="orm:temporal" minOccurs="0"/&gt;
 *  &lt;xsd:element name="map-key-enumerated" type="orm:enumerated" minOccurs="0"/&gt;
 *  &lt;xsd:element name="map-key-convert" type="xsd:string" minOccurs="0"/&gt;
 *  &lt;xsd:sequence&gt;
 *  &lt;xsd:element name="map-key-attribute-override" type="orm:attribute-override" minOccurs="0" maxOccurs="unbounded"/&gt;
 *  &lt;xsd:element name="map-key-association-override" type="orm:association-override" minOccurs="0" maxOccurs="unbounded"/&gt;
 *  &lt;/xsd:sequence&gt;
 *  &lt;/xsd:choice&gt;
 *  &lt;xsd:choice&gt;
 *  &lt;xsd:element name="map-key-column" type="orm:map-key-column" minOccurs="0"/&gt;
 *  &lt;xsd:element name="map-key-join-column" type="orm:map-key-join-column" minOccurs="0" maxOccurs="unbounded"/&gt;
 *  &lt;/xsd:choice&gt;
 *  &lt;/xsd:sequence&gt;
 *  &lt;/xsd:choice&gt;
 *  &lt;xsd:choice&gt;
 *  &lt;xsd:sequence&gt;
 *  &lt;xsd:element name="column" type="orm:column" minOccurs="0"/&gt;
 *  &lt;xsd:choice&gt;
 *  &lt;xsd:element name="temporal" type="orm:temporal" minOccurs="0"/&gt;
 *  &lt;xsd:element name="enumerated" type="orm:enumerated" minOccurs="0"/&gt;
 *  &lt;xsd:element name="lob" type="orm:lob" minOccurs="0"/&gt;
 *  &lt;xsd:element name="convert" type="xsd:string" minOccurs="0"/&gt;
 *  &lt;/xsd:choice&gt;
 *  &lt;/xsd:sequence&gt;
 *  &lt;xsd:sequence&gt;
 *  &lt;xsd:element name="attribute-override" type="orm:attribute-override" minOccurs="0" maxOccurs="unbounded"/&gt;
 *  &lt;xsd:element name="association-override" type="orm:association-override" minOccurs="0" maxOccurs="unbounded"/&gt;
 *  &lt;/xsd:sequence&gt;
 *  &lt;/xsd:choice&gt;
 *  &lt;xsd:choice minOccurs="0" maxOccurs="2"&gt;
 *  &lt;xsd:element name="converter" type="orm:converter"/&gt;
 *  &lt;xsd:element name="type-converter" type="orm:type-converter"/&gt;
 *  &lt;xsd:element name="object-type-converter" type="orm:object-type-converter"/&gt;
 *  &lt;xsd:element name="struct-converter" type="orm:struct-converter"/&gt;
 *  &lt;/xsd:choice&gt;
 *  &lt;xsd:element name="collection-table" type="orm:collection-table" minOccurs="0"/&gt;
 *  &lt;xsd:element name="join-fetch" type="orm:join-fetch-type" minOccurs="0"/&gt;
 *  &lt;xsd:element name="batch-fetch" type="orm:batch-fetch" minOccurs="0"/&gt;
 *  &lt;xsd:element name="property" type="orm:property" minOccurs="0" maxOccurs="unbounded"/&gt;
 *  &lt;xsd:element name="access-methods" type="orm:access-methods" minOccurs="0"/&gt;
 *  &lt;/xsd:sequence&gt;
 *  &lt;xsd:attribute name="name" type="xsd:string" use="required"/&gt;
 *  &lt;xsd:attribute name="target-class" type="xsd:string"/&gt;
 *  &lt;xsd:attribute name="fetch" type="orm:fetch-type"/&gt;
 *  &lt;xsd:attribute name="access" type="orm:access-type"/&gt;
 *  &lt;xsd:attribute name="attribute-type" type="xsd:string"/&gt;
 *  &lt;/xsd:complexType&gt;
 *
 *
 * </pre>
 *
 * @author Michael Fortin
 */
public final class ElementCollection extends Node {
    private String name =  "";
    private ORM.Fetch fetch =  ORM.Fetch.LAZY;
    private Class targetClass =  void.class;

    public ElementCollection() {
        super(2,7);
    }

    public ElementCollection(String name, Class targetClass) {
        this();
        this.name = name;
        this.targetClass = targetClass;
    }

    @Override
    public void appendElement(Document root, Element parent) {
        Element node = root.createElement("element-collection");
        node.setAttribute("name", name);
        node.setAttribute("target-class", targetClass.getName());
        node.setAttribute("fetch", fetch.toString());
        children.stream().forEach(n->n.appendElement(root,node));
        parent.appendChild(node);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(1,3)
                .append(name)
                .append(fetch)
                .append(targetClass)
                .hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ElementCollection)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        ElementCollection rhs = (ElementCollection) obj;
        return new EqualsBuilder()
                .append(this.name, rhs.name)
                .append(this.fetch, rhs.fetch)
                .append(this.targetClass, rhs.targetClass)
                .isEquals();
    }
}
