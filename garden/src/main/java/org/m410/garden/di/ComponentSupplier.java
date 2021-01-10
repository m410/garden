package org.m410.garden.di;

import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;
import org.m410.garden.zone.ZoneManager;


/**
 * @author Michael Fortin
 */
@FunctionalInterface
public interface ComponentSupplier {
    Components get(ZoneManager zoneManager, ImmutableHierarchicalConfiguration configuration);
}
