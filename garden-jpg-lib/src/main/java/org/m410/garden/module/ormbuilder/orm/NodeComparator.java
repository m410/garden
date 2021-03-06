package org.m410.garden.module.ormbuilder.orm;

import java.util.Comparator;


/**
 * Use to order and sort nodes so the orm.xml file puts the elements in the
 * right order.
 */
public final class NodeComparator implements Comparator<Node> {
    @Override
    public int compare(Node o1, Node o2) {
        return o1.compareTo(o2);
    }
}
