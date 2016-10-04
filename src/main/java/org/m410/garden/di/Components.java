package org.m410.garden.di;


import com.google.common.collect.ImmutableList;
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

    // todo create Components.of(Component...).withZone(ZoneManager z);

    private final List<Component> components;
    private final List<ZoneHandlerFactory> zoneHandlerFactories;
    private final SortedSet<Entry> registry = new TreeSet<>();

    private Components(List<Component> components, List<ZoneHandlerFactory> zoneHandlerFactories) {
        this.components = components;
        this.zoneHandlerFactories = zoneHandlerFactories;
    }

    public static Components init() {
        return new Components(new ArrayList<>(), new ArrayList<>());
    }

    public static Components of(Component... components) {
        ImmutableList<Component> build = ImmutableList.<Component>builder().addAll(Arrays.asList(components)).build();
        return new Components(build, ImmutableList.of());
    }

    // todo should this be the zoneManager?
    public Components withZoneHandler(ZoneHandlerFactory zoneHandlerFactory) {
        final List<ZoneHandlerFactory> out = ImmutableList.<ZoneHandlerFactory>builder()
                .addAll(zoneHandlerFactories)
                .add(zoneHandlerFactory)
                .build();
        return new Components(components, out);
    }

    public Components inherit(Components components) {
        this.registry.addAll(components.registry);
        return this;
    }

    public Components add(Component component) {
        final List<Component> out = ImmutableList.<Component>builder()
                .addAll(components)
                .add(component)
                .build();
        return new Components(out, zoneHandlerFactories);
    }

    public Components make() {
        final List<ComponentBuilder> builders = components.stream()
                .flatMap(c -> c.builders().stream())
                .collect(Collectors.toList());

        while (builders.size() > registry.size()) {
            final Collection<Entry> collect = builders.stream()
                    .filter(builder -> !builder.isRegistered(registry))
                    .filter(builder -> builder.canCreateWith(registry))
                    .map(builder -> builder.createWith(zoneHandlerFactories, registry))
                    .collect(Collectors.toList());

            registry.addAll(collect);
        }

        return this;
    }

    @Override
    public <T> T typeOf(Class<T> myServiceClass) {
        return registry.stream()
                .filter(entry -> entry.getType().equals(myServiceClass))
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
    static final class Entry implements Comparable<Entry> {
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

        @Override
        public int compareTo(Entry o) {
            // todo needs improvement
            return this.name.compareTo(o.name);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Entry entry = (Entry) o;

            if (!name.equals(entry.name)) {
                return false;
            }
            if (!type.equals(entry.type)) {
                return false;
            }
            return instance.equals(entry.instance);

        }

        @Override
        public int hashCode() {
            int result = name.hashCode();
            result = 31 * result + type.hashCode();
            result = 31 * result + instance.hashCode();
            return result;
        }
    }
}
