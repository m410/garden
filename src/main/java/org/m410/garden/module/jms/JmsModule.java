package org.m410.garden.module.jms;

import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;
import org.m410.garden.application.ApplicationModule;
import org.m410.garden.application.annotate.ComponentsProvider;
import org.m410.garden.di.ComponentSupplier;
import org.m410.garden.di.Components;

/**
 * Add JMS functionality to the application.  This is a non-working
 * mock up.
 *
 * @author Michael Fortin
 */
public interface JmsModule extends ApplicationModule {

    @ComponentsProvider
    static ComponentSupplier makeJmsServices(final ImmutableHierarchicalConfiguration c) {
        return (zoneManager, configuration) -> Components.init();
    }
}
