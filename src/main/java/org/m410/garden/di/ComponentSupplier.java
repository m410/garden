package org.m410.garden.di;

import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;

/**
 * @author Michael Fortin
 */
@FunctionalInterface
public interface ComponentSupplier {
    Components get(InvocationHandlerFactory invocationHandlerFactory, ImmutableHierarchicalConfiguration configuration);
}
