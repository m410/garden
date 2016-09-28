package org.m410.garden.di;

import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;

import javax.servlet.Filter;
import java.util.Collection;

/**
 * @author Michael Fortin
 */
@FunctionalInterface
public interface FilterSupplier {
    Collection<? extends Filter> get(ImmutableHierarchicalConfiguration configuration);
}
