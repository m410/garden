package org.m410.garden.di;

/**
 * @author Michael Fortin
 */
public final class ComponentBuilder<T> {
    private Class[] dependencies = new Class[0];
    private ComponentFactory<T> factory;

    ComponentBuilder(Class[] dependencies, ComponentFactory<T> factory) {
        this.dependencies = dependencies;
        this.factory = factory;
    }

    public static <T> ComponentBuilder<T> builder(Class<T> t) {
        return new ComponentBuilder<T>(null,null);
    }

    public ComponentBuilder<T> dependsOn(Class... dependency) {
        return new ComponentBuilder<T>(dependency,factory);
    }

    public ComponentBuilder<T> constructor(ComponentFactory<T> factory) {
        return new ComponentBuilder<T>(dependencies,factory);
    }
}
