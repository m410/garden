package org.m410.garden.application;

import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;
import org.m410.garden.servlet.FilterDefinition;

import java.util.Collection;

/**
 * @author Michael Fortin
 */
@FunctionalInterface
public interface FilterSupplier {
    Collection<FilterDefinition> get(ImmutableHierarchicalConfiguration configuration);
}
