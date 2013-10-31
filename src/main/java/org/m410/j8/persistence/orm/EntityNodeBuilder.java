package org.m410.j8.persistence.orm;

import java.util.Arrays;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class EntityNodeBuilder {
    private Entity entityNode;
    private Node attributes = new Attributes();

    public EntityNodeBuilder(String ormClassName, String table) {
        this.entityNode = new Entity(ormClassName);
        this.entityNode.addChild(new Table(table));
        this.entityNode.addChild(attributes);
    }

    public EntityNodeBuilder id(String id, Node... nodes) {
        final Id node = new Id(id);
        return append(node, nodes);
    }

    public EntityNodeBuilder id(String id) {
        return append(new Id(id), new Node[0]);
    }

    public EntityNodeBuilder basic(String id, Node... nodes) {
        final Basic node = new Basic(id);
        return append(node, nodes);
    }

    public EntityNodeBuilder basic(String id) {
        return append(new Basic(id), new Node[0]);
    }

    public EntityNodeBuilder version(String id, Node... nodes) {
        final Version node = new Version(id);
        return append(node, nodes);
    }

    public EntityNodeBuilder version(String id) {
        return append(new Version(id), new Node[0]);
    }

    public EntityNodeBuilder manyToOne(String id, Node... nodes) {
        return this;
    }

    public EntityNodeBuilder oneToMany(String id, Node... nodes) {
        return this;
    }

    public EntityNodeBuilder manyToMany(String id, Node... nodes) {
        return this;
    }

    public EntityNodeBuilder oneToOne(String id, Node... nodes) {
        return this;
    }

    private EntityNodeBuilder append(Node node, Node[] children) {
        attributes.addChild(node);
        Arrays.asList(children).stream().forEach(node::addChild);
        return this;
    }

    public Entity make() {
        return entityNode;
    }
}
