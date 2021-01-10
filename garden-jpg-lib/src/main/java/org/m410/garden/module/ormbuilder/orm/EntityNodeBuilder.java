package org.m410.garden.module.ormbuilder.orm;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.function.Function;

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

//    public <T, V> EntityNodeBuilder id(Function<T, V> idGetter, Node... nodes) {
//        final Id node = new Id((new TypeReader<V>(){}).type());
//        return appendAttribute(node, nodes);
//    }
//
//    abstract class TypeReader<T>{
//        @SuppressWarnings("unchecked")
//        public Class<T> type(){
//            return ((Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
//        }
//    }

    public EntityNodeBuilder id(String id, Node... nodes) {
        final Id node = new Id(id);
        return appendAttribute(node, nodes);
    }

    public EntityNodeBuilder id(String id) {
        return appendAttribute(new Id(id), new Node[0]);
    }

    public EntityNodeBuilder basic(String id, Node... nodes) {
        final Basic node = new Basic(id);
        return appendAttribute(node, nodes);
    }

    public EntityNodeBuilder basic(String id) {
        return appendAttribute(new Basic(id), new Node[0]);
    }

    public EntityNodeBuilder version(String id, Node... nodes) {
        final Version node = new Version(id);
        return appendAttribute(node, nodes);
    }

    public EntityNodeBuilder version(String id) {
        return appendAttribute(new Version(id), new Node[0]);
    }

    public EntityNodeBuilder namedQuery(String name,String query) {
        return appendEntity(new NamedQuery(name, query), new Node[0]);
    }

    //    calista
    public <T> EntityNodeBuilder elementCollection(String name,Class<T> target, Node... nodes) {
        return appendAttribute(new ElementCollection(name, target), nodes);
    }

    public <T> EntityNodeBuilder manyToOne(String name, Class<T> target, Node... nodes) {
        return appendAttribute(new ManyToOne(name, target), nodes);
    }

    public <T> EntityNodeBuilder manyToOne(String name, Class<T> target, String mappedBy) {
        return appendAttribute(new ManyToOne(name, target, mappedBy), new Node[]{});
    }

    public <T> EntityNodeBuilder oneToMany(String name, Class<T> target, String mappedBy) {
        return appendAttribute(new OneToMany(name, target, mappedBy), new Node[]{});
    }

    public <T> EntityNodeBuilder oneToMany(String name, Class<T> target, Node... nodes) {
        return appendAttribute(new OneToMany(name, target), nodes);
    }

    public <T> EntityNodeBuilder manyToMany(String name, Class<T> target, String mappedBy, Node... nodes) {
        return appendAttribute(new ManyToMany(name, target, mappedBy), nodes);

    }

    public <T> EntityNodeBuilder oneToOne(String name, Class<T> target, String mappedBy) {
        return appendAttribute(new OneToOne(name, target, mappedBy), new Node[]{});
    }

    public <T> EntityNodeBuilder oneToOne(String name, Class<T> target, Node... nodes) {
        return appendAttribute(new OneToOne(name, target), nodes);
    }

    private EntityNodeBuilder appendAttribute(Node node, Node[] children) {
        attributes.addChild(node);
        Arrays.asList(children).stream().forEach(node::addChild);
        return this;
    }

    private EntityNodeBuilder appendEntity(Node node, Node[] children) {
        Arrays.asList(children).stream().forEach(node::addChild);
        entityNode.addChild(node);
        return this;
    }

    public Entity make() {
        return entityNode;
    }
}
