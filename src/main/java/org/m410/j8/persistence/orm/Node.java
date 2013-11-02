package org.m410.j8.persistence.orm;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.*;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
abstract public class Node implements Comparable<Node> {
    protected int listOrder;
    protected Set<Node> children = new TreeSet<>();

    protected Node(int listOrder) {
        this.listOrder = listOrder;
    }

    public void addChild(Node node) {
        children.add(node);
    }

    public Set<Node> getChildren() {
        return children;
    }

    abstract public void appendElement(Document root, Element parent);
//    {
//        Element node = root.createElement("name");
//        parent.appendChild(node);
//        children.stream().forEach(n->n.toElement(root,node));
//        return node;
//    }

    @Override
    public int compareTo(Node o) {
        return new CompareToBuilder().append(this.listOrder, o.listOrder).toComparison();
    }
}
