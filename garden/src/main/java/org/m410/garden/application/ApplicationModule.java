package org.m410.garden.application;

import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;
import org.m410.garden.controller.action.http.HttpActionDefinition;
import org.m410.garden.di.*;
import org.m410.garden.servlet.FilterDefinition;
import org.m410.garden.servlet.ListenerDefinition;
import org.m410.garden.servlet.ServletDefinition;
import org.m410.garden.zone.ZoneFactory;
import org.m410.garden.zone.ZoneFactorySupplier;

import java.util.List;

/**
 * This the base interface for the application and all modules.  By extending
 * this in another interface and using default methods you can mimic
 * multiple inheritance.
 *
 * @author Michael Fortin
 */
public interface ApplicationModule {

    ServletSupplier servletProvider();

    FilterSupplier filterProvider();

    ListenerSupplier listenerProvider();

    /**
     * Creates service classes with the available configuration.  Some modules may add
     * services through this method.
     * <p>
     * It is not explicitly necessary for you to add your service here unless you
     * require lifecycle management.  Note lifecycle management is not fully implemented yet.
     *
     * @return a list of service classes.
     */
    ComponentSupplier componentProvider();

    ControllerSupplier controllerProvider();

    ZoneFactorySupplier zoneFactoryProvider();


    List<ListenerDefinition> getListeners();

    List<FilterDefinition> getFilters();

    List<ServletDefinition> getServlets();

    List<? extends HttpActionDefinition> getActionDefinitions();

    /**
     * This is the main initialization method for the application.  It will be
     * called by the {@link ApplicationContextListener} on startup.  This, in turn,
     * calls the other factory methods using the passed in configuration.
     *
     * @param configuration configuration
     */
    void init(ImmutableHierarchicalConfiguration configuration);

    void destroy();
}
