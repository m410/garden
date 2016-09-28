package org.m410.garden.di;


import java.lang.reflect.InvocationHandler;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Michael Fortin
 */
public final class Components {

    private final List<Component> components;
    private final List<InvocationHandlerFactory> invocationHandlers;
    private final SortedSet<Entry> registry = new TreeSet<>();

    private Components(List<Component> components, List<InvocationHandlerFactory> invocationHandlers) {
        this.components = components;
        this.invocationHandlers = invocationHandlers;
    }

    public static Components init() {
        return new Components(new ArrayList<>(), new ArrayList<>());
    }

    public Components withProxy(InvocationHandlerFactory o) {
        List<InvocationHandlerFactory> transactionAspectList2 = new ArrayList<>();
        transactionAspectList2.addAll(invocationHandlers);
        transactionAspectList2.add(o);
        List<Component> componentList2 = new ArrayList<>();
        componentList2.addAll(components);

        return new Components(componentList2, transactionAspectList2);
    }

    public Components withComponents(Component component) {
        List<InvocationHandlerFactory> transactionAspectList2 = new ArrayList<>();
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

    public <T> T typeOf(Class<T> myServiceClass) {
        return registry.stream()
                .filter(r -> r.getClass().isAssignableFrom(myServiceClass))
                .findAny()
                .orElseThrow(() -> new NoServiceException(myServiceClass.getName()))
                .getInstanceAs(myServiceClass);
    }

    static final class Entry {
        private final String name;
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
