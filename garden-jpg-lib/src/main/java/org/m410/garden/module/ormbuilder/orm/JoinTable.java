package org.m410.garden.module.ormbuilder.orm;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Arrays;

/**
 * An orm.xml node.
 *
 * <pre>
 *  &lt;xsd:complexType name="join-table"&gt;
 *    &lt;xsd:annotation&gt;
 *    &lt;xsd:documentation&gt;
 *      Target({METHOD, FIELD}) @Retention(RUNTIME) public @interface JoinTable {
 *        String name() default "";
 *        String catalog() default "";
 *        String schema() default "";
 *        JoinColumn[] joinColumns() default {};
 *        JoinColumn[] inverseJoinColumns() default {};
 *        UniqueConstraint[] uniqueConstraints() default {};
 *        Index[] indexes() default {};
 *    }
 *    &lt;/xsd:documentation&gt;
 *    &lt;/xsd:annotation&gt;
 *    &lt;xsd:sequence&gt;
 *    &lt;xsd:sequence&gt;
 *    &lt;xsd:element name="join-column" type="orm:join-column" minOccurs="0" maxOccurs="unbounded" /&gt;
 *    &lt;xsd:element name="foreign-key" type="orm:foreign-key" minOccurs="0" /&gt;
 *    &lt;/xsd:sequence&gt;
 *    &lt;xsd:sequence&gt;
 *    &lt;xsd:element name="inverse-join-column" type="orm:join-column" minOccurs="0" maxOccurs="unbounded" /&gt;
 *    &lt;xsd:element name="inverse-foreign-key" type="orm:foreign-key" minOccurs="0" /&gt;
 *    &lt;/xsd:sequence&gt;
 *    &lt;xsd:element name="unique-constraint" type="orm:unique-constraint" minOccurs="0" maxOccurs="unbounded" /&gt;
 *    &lt;xsd:element name="index" type="orm:index" minOccurs="0" maxOccurs="unbounded" /&gt;
 *    &lt;/xsd:sequence&gt;
 *    &lt;xsd:attribute name="name" type="xsd:string" /&gt;
 *    &lt;xsd:attribute name="catalog" type="xsd:string" /&gt;
 *    &lt;xsd:attribute name="schema" type="xsd:string" /&gt;
 *    &lt;/xsd:complexType&gt;
 * </pre>
 *
 * @author Michael Fortin
 */
public final class JoinTable extends Node {
    private String name =  "";
    private String catalog =  "";
    private String schema =  "";

    public JoinTable() {
        super(3,2);
    }

    public JoinTable(String name, String catalog, String schema) {
        this();
        this.name = name;
        this.catalog = catalog;
        this.schema = schema;
    }

    public JoinTable(String name) {
        this();
        this.name = name;
    }

    public JoinTable withChildren(Node... nodes) {
        this.children.addAll(Arrays.asList(nodes));
        return this;
    }

    @Override
    public void appendElement(Document root, Element parent) {
        Element basic = root.createElement("join-table");
        basic.setAttribute("name",name);

        if(!"".equals(catalog))
            basic.setAttribute("catalog", catalog);

        if(!"".equals(schema))
            basic.setAttribute("schema", schema);

        children.stream().forEach(n->n.appendElement(root,basic));
        parent.appendChild(basic);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(1,3)
                .append(name)
                .append(catalog)
                .append(schema)
                .hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof JoinTable)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        JoinTable rhs = (JoinTable) obj;
        return new EqualsBuilder()
                .append(this.name, rhs.name)
                .append(this.catalog, rhs.catalog)
                .append(this.schema, rhs.schema)
                .isEquals();
    }
}
