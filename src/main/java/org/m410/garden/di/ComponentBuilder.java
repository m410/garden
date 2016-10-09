package org.m410.garden.di;

import org.m410.garden.zone.ZoneManager;

import java.util.Arrays;
import java.util.Optional;
import java.util.SortedSet;
import java.util.stream.Collectors;

/**
 * @author Michael Fortin
 */
// todo should break out interface & make this inner class of Component
public final class ComponentBuilder<T> {
    private final Class[] dependencies;
    private final ComponentFactory<T> factory;
    private final Class<T> targetClass;

    // need to specify which ZoneHandlerFactories to apply to the ComponentFactory

    private ComponentBuilder(Class<T> targetClass, Class[] dependencies, ComponentFactory<T> factory) {
        this.dependencies = dependencies;
        this.factory = factory;
        this.targetClass = targetClass;
    }

    public static <T> ComponentBuilder<T> builder(Class<T> t) {
        return new ComponentBuilder<T>(t, new Class[0], null);
    }

    public ComponentBuilder<T> dependsOn(Class... dependency) {
        return new ComponentBuilder<T>(targetClass, dependency,factory);
    }

    public ComponentBuilder<T> factory(ComponentFactory<T> factory) {
        return new ComponentBuilder<T>(targetClass, dependencies,factory);
    }

    boolean isRegistered(SortedSet<Components.Entry> registry) {
        return registry.stream().filter(r->r.getType().equals(this.targetClass)).findAny().isPresent();
    }

    boolean canCreateWith(SortedSet<Components.Entry> registry) {
        final int size = Arrays.stream(dependencies)
                .filter(d -> registryContains(d, registry).isPresent())
                .collect(Collectors.toList())
                .size();
        return size == dependencies.length;
    }

    private Optional<Components.Entry> registryContains(Class d, SortedSet<Components.Entry> registry) {
        return registry.stream().filter(r->r.getType().equals(d)).findAny();
    }

    // todo should proxy be a list?  Maybe pass the whole zoneManager
    Components.Entry createWith(ZoneManager zone, SortedSet<Components.Entry> registry) {
        Object[] arguments = Arrays.stream(dependencies)
                .map(d -> registryContains(d, registry).get().getInstance())
                .toArray();

        final T instance = factory.make(zone, arguments);
        return new Components.Entry(targetClass.getSimpleName(), targetClass, instance);
    }

    public Class[] getDependencies() {
        return dependencies;
    }

    public Class<T> getTargetClass() {
        return targetClass;
    }
}
