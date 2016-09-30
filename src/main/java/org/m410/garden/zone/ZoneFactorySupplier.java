package org.m410.garden.zone;

import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;

import java.util.Collection;

/**
 * @author Michael Fortin
 */
@FunctionalInterface
public interface ZoneFactorySupplier {
    Collection<? extends ZoneFactory> get(ImmutableHierarchicalConfiguration configuration);
}
