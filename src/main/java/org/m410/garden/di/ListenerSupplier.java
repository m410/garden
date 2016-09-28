package org.m410.garden.di;

import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;

import java.util.Collection;
import java.util.EventListener;

/**
 * @author Michael Fortin
 */
@FunctionalInterface
public interface ListenerSupplier {
    Collection<? extends EventListener> get(ImmutableHierarchicalConfiguration configuration);
}
