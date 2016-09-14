package org.m410.garden.di;

import java.lang.reflect.InvocationHandler;
import java.util.Arrays;
import java.util.Optional;
import java.util.SortedSet;
import java.util.stream.Collectors;

/**
 * @author Michael Fortin
 */
public final class ComponentBuilder<T> {
    private final Class[] dependencies;
    private final ComponentFactory<T> factory;
    private final Class<T> targetClass;

    ComponentBuilder(Class<T> targetClass, Class[] dependencies, ComponentFactory<T> factory) {
        this.dependencies = dependencies;
        this.factory = factory;
        this.targetClass = targetClass;
    }

    public static <T> ComponentBuilder<T> builder(Class<T> t) {
        return new ComponentBuilder<T>(t, null,null);
    }

    public ComponentBuilder<T> dependsOn(Class... dependency) {
        return new ComponentBuilder<T>(targetClass, dependency,factory);
    }

    public ComponentBuilder<T> constructor(ComponentFactory<T> factory) {
        return new ComponentBuilder<T>(targetClass, dependencies,factory);
    }

    public boolean isRegistered(SortedSet<Components.Entry> registry) {
        return registry.stream().filter(r->r.getType().equals(this.targetClass)).findAny().isPresent();
    }

    public boolean canCreateWith(SortedSet<Components.Entry> registry) {
        final int size = Arrays.stream(dependencies)
                .filter(d -> registryContains(d, registry).isPresent())
                .collect(Collectors.toList())
                .size();
        return size == dependencies.length;
    }

    private Optional<Components.Entry> registryContains(Class d, SortedSet<Components.Entry> registry) {
        return registry.stream().filter(r->r.getType().equals(d)).findAny();
    }

    public Components.Entry createWith(InvocationHandlerFactory proxy, SortedSet<Components.Entry> registry) {
        Object[] arguments = Arrays.stream(dependencies).map(d->registryContains(d, registry).get()).toArray();
        final T instance = factory.make(proxy, arguments);
        return new Components.Entry(targetClass.getSimpleName(), targetClass, instance);
    }
}
