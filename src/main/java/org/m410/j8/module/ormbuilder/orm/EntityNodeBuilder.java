package org.m410.j8.module.ormbuilder.orm;

import java.util.Arrays;

/**
 * Builder design pattern to create a Entity Node.  This allows you to add top level
 * properties and relationships to a entity much the same way as adding annotations.
 *
 * @author Michael Fortin
 */
public final class EntityNodeBuilder {
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

    public EntityNodeBuilder manyToOne(String name, Node... nodes) {
        return append(new ManyToOne(name),nodes);
    }

    public <T> EntityNodeBuilder oneToMany(String name, Class<T> target, String mappedBy) {
        return append(new OneToMany(name, target, mappedBy),new Node[]{});
    }

    public <T> EntityNodeBuilder manyToMany(String name, Class<T> target, String mappedBy, Node... nodes) {
        return append(new ManyToMany(name, target, mappedBy),nodes);

    }

    public <T> EntityNodeBuilder oneToOne(String name, Class<T> target, String mappedBy, Node... nodes) {
        return append(new OneToOne(name, target, mappedBy),nodes);
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
