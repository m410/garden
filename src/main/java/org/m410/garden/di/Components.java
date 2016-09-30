package org.m410.garden.di;


import org.m410.garden.zone.ZoneHandlerFactory;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A registry of multiple component instances.
 *
 * @author Michael Fortin
 */
public final class Components implements ComponentRegistry {

    private final List<Component> components;
    private final List<ZoneHandlerFactory> invocationHandlers;
    private final SortedSet<Entry> registry = new TreeSet<>();

    private Components(List<Component> components, List<ZoneHandlerFactory> invocationHandlers) {
        this.components = components;
        this.invocationHandlers = invocationHandlers;
    }

    public static Components init() {
        return new Components(new ArrayList<>(), new ArrayList<>());
    }

    public Components withProxy(ZoneHandlerFactory o) {
        List<ZoneHandlerFactory> transactionAspectList2 = new ArrayList<>();
        transactionAspectList2.addAll(invocationHandlers);
        transactionAspectList2.add(o);
        List<Component> componentList2 = new ArrayList<>();
        componentList2.addAll(components);

        return new Components(componentList2, transactionAspectList2);
    }

    public Components inherit(Components components) {
        this.registry.addAll(components.registry);
        return this;
    }

    public Components add(Component component) {
        List<ZoneHandlerFactory> transactionAspectList2 = new ArrayList<>();
        transactionAspectList2.addAll(invocationHandlers);
        List<Component> componentList2 = new ArrayList<>();
        componentList2.add(component);
        componentList2.addAll(components);

        return new Components(componentList2, transactionAspectList2);
    }

    public Components make() {
        final List<ComponentBuilder> builders = components.stream()
                .flatMap(c -> c.builders().stream())
                .collect(Collectors.toList());

        while (builders.size() < registry.size()) {
            final Collection<Entry> collect = builders.stream()
                    .filter(b -> b.isRegistered(registry))
                    .filter(b -> b.canCreateWith(registry))
                    .map(b -> b.createWith(invocationHandlers.get(0),registry))
                    .collect(Collectors.toList());

            registry.addAll(collect);
        }

        return this;
    }

    @Override
    public <T> T typeOf(Class<T> myServiceClass) {
        return registry.stream()
                .filter(r -> r.getClass().isAssignableFrom(myServiceClass))
                .findAny()
                .orElseThrow(() -> new NoServiceException(myServiceClass.getName()))
                .getInstanceAs(myServiceClass);
    }

    @Override
    public <T> T typeOf(Class<T> myServiceClass, String name) {
        return registry.stream()
                .filter(r -> r.getClass().isAssignableFrom(myServiceClass))
                .filter(r -> r.getName().equals(name))
                .findAny()
                .orElseThrow(() -> new NoServiceException(myServiceClass.getName() +" and '"+name+"'"))
                .getInstanceAs(myServiceClass);
    }

    @Override
    public <T> T[] arrayOf(Class<T> myServiceClass) {
        return registry.stream().filter(r -> r.getClass().isAssignableFrom(myServiceClass))
                .toArray(size -> (T[])Array.newInstance(myServiceClass,size));
    }

    /**
     *
     */
    static final class Entry {
        private final String name;
        // todo add support for multiple types
        private final Class type;
        private final Object instance;

        public Entry(String name, Class type, Object instance) {
            this.name = name;
            this.type = type;
            this.instance = instance;
        }

        public Entry(Class type, Object instance) {
            this.type = type;
            this.instance = instance;
            this.name = type.getSimpleName();
        }

        public String getName() {
            return name;
        }

        public Class getType() {
            return type;
        }

        public Object getInstance() {
            return instance;
        }

        public <T> T getInstanceAs(Class<T> myServiceClass) {
            return (T) instance;
        }
    }
}
