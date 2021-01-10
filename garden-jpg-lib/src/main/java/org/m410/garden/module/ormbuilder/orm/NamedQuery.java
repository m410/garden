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
 * &lt;xsd:complexType name="named-query"&gt;
 *   &lt;xsd:annotation&gt;
 *   &lt;xsd:documentation&gt;
 *   Target({TYPE}) @Retention(RUNTIME)
 *       public @interface NamedQuery {
 *       String name();
 *       String query();
 *       LockModeType lockMode() default NONE;
 *       QueryHint[] hints() default {};
 *   }
 *   &lt;/xsd:documentation&gt;
 *   &lt;/xsd:annotation&gt;
 *   &lt;xsd:sequence&gt;
 *   &lt;xsd:element name="description" type="xsd:string" minOccurs="0"/&gt;
 *   &lt;xsd:element name="query" type="xsd:string"/&gt;
 *   &lt;xsd:element name="lock-mode" type="orm:lock-mode-type" minOccurs="0"/&gt;
 *   &lt;xsd:element name="hint" type="orm:query-hint"
 *   minOccurs="0" maxOccurs="unbounded"/&gt;
 *   &lt;/xsd:sequence&gt;
 *   &lt;xsd:attribute name="name" type="xsd:string" use="required"/&gt;
 * &lt;/xsd:complexType&gt;
 *
 * </pre>
 *
 * @author Michael Fortin
 */
public final class NamedQuery extends Node {

    String name;
    String query;

    NamedQuery() {
        super(1,1);
    }

    public NamedQuery(String name, String query) {
        this();
        this.name = name;
        this.query = query;
    }

    @Override
    public void appendElement(Document root, Element parent) {
        Element namedQuery = root.createElement("named-query");
        namedQuery.setAttribute("name",name);

        Element queryElem = root.createElement("query");
        queryElem.setTextContent(query);
        namedQuery.appendChild(queryElem);

        parent.appendChild(namedQuery);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(5,5)
                .append(name)
                .append(query)
                .hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof NamedQuery)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        NamedQuery rhs = (NamedQuery) obj;
        return new EqualsBuilder()
                .append(this.name, rhs.name)
                .append(this.query, rhs.query)
                .isEquals();
    }
}
