package org.m410.garden.di;

import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;
import org.m410.garden.transaction.ThreadLocalSessionFactory;

import java.util.Collection;

/**
 * @author Michael Fortin
 */
@FunctionalInterface
public interface ThreadLocalSupplier {
    Collection<? extends ThreadLocalSessionFactory> get(ImmutableHierarchicalConfiguration configuration);
}
