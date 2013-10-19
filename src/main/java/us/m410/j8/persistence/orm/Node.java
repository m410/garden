package us.m410.j8.persistence.orm;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.util.ArrayList;
import java.util.List;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
abstract public class Node {
    List<Node> children = new ArrayList<>();

    public void addChild(Node node) {
        children.add(node);
    }

    abstract public void appendElement(Document root, Element parent);
//    {
//        Element node = root.createElement("name");
//        parent.appendChild(node);
//        children.stream().forEach(n->n.toElement(root,node));
//        return node;
//    }
}
