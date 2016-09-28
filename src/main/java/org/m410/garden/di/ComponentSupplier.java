package org.m410.garden.di;

import org.m410.garden.configuration.Configuration;

/**
 * @author Michael Fortin
 */
@FunctionalInterface
public interface ComponentSupplier {
    Components get(InvocationHandlerFactory invocationHandlerFactory, Configuration configuration);
}
