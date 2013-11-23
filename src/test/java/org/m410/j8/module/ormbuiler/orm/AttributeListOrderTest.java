package org.m410.j8.module.ormbuiler.orm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.*;

import static org.junit.Assert.*;


@RunWith(JUnit4.class)
public class AttributeListOrderTest {

    @Test
    public void testSorting() {
        List<Node> set = new ArrayList<>();
        set.add(new Basic("b"));
        set.add(new Id("id"));
        set.add(new Version("v"));

        set.sort(new NodeComparator());
        Node n = set.get(0);
        assertEquals(new Id("id"), n);
    }
    @Test
    public void testOrdering() {
        Attributes attributes = new Attributes();
        attributes.addChild(new Basic("b"));
        attributes.addChild(new Id("id"));
        attributes.addChild(new Version("v"));

        final Iterator<Node> iterator = attributes.getChildren().stream().sorted().iterator();
        assertTrue(iterator.hasNext());
        assertEquals(new Id("id"),iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(new Basic("b"), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(new Version("v"), iterator.next());
    }
}
