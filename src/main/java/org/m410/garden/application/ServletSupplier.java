package org.m410.garden.application;

import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;
import org.m410.garden.servlet.ServletDefinition;

import java.util.Collection;

/**
 * @author Michael Fortin
 */
@FunctionalInterface
public interface ServletSupplier {
    Collection<ServletDefinition> get(ImmutableHierarchicalConfiguration configuration);
}
