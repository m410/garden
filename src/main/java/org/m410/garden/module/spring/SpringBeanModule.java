package org.m410.garden.module.spring;

import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;
import org.m410.garden.application.ApplicationModule;
import org.m410.garden.application.annotate.ComponentsProvider;
import org.m410.garden.di.ComponentSupplier;
import org.m410.garden.di.Components;

/**
 * This is not implemented.  The concept is that if you would prefer
 * to use spring bean annotations to wire your class instead of
 * explicit wiring you you would have that option simply by adding
 * this module to your application.
 *
 * todo not implemented
 *
 * @author Michael Fortin
 */
public interface SpringBeanModule extends ApplicationModule {

    @ComponentsProvider
    static ComponentSupplier makeSpringServices(final ImmutableHierarchicalConfiguration c) {
        return (zoneManager, configuration) -> Components.init();
    }
}
