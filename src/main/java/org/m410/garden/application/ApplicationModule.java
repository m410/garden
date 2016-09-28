package org.m410.garden.application;

import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;
import org.m410.garden.controller.HttpCtlr;
import org.m410.garden.controller.action.http.HttpActionDefinition;
import org.m410.garden.servlet.FilterDefinition;
import org.m410.garden.servlet.ListenerDefinition;
import org.m410.garden.servlet.ServletDefinition;
import org.m410.garden.transaction.ThreadLocalSessionFactory;

import java.util.List;

/**
 * This the base interface for the application and all modules.  By extending
 * this in another interface and using default methods you can mimic
 * multiple inheritance.
 *
 * @author Michael Fortin
 */
public interface ApplicationModule {

    /**
     * Add container listeners to the servlet container at startup.
     *
     * @param c configuration
     * @return a list of listener definitions
     */
    List<ListenerDefinition> makeListeners(ImmutableHierarchicalConfiguration c);

    List<ListenerDefinition> getListeners();

    /**
     * creates a list of servlet filters that are added to the servlet
     * container at startup.
     *
     * @param c configuration
     * @return list of filter definitions
     */
    List<FilterDefinition> makeFilters(ImmutableHierarchicalConfiguration c) ;

    List<FilterDefinition> getFilters();

    /**
     * creates a list of servlet definitions that are added to the servlet
     * container at startup.
     *
     * @param c configuration
     * @return a list of servlet definitions
     */
    List<ServletDefinition> makeServlets(ImmutableHierarchicalConfiguration c) ;

    List<ServletDefinition> getServlets();

    /**
     * Creates the threadLocal factories for the application.
     *
     * @param c configuration
     * @return a list of thread local factory implementations.
     */
    List<? extends ThreadLocalSessionFactory> makeThreadLocalFactories(ImmutableHierarchicalConfiguration c);

    List<? extends ThreadLocalSessionFactory> getThreadLocalFactories();

    /**
     * Creates the controllers for the application.
     *
     * @param c configuration
     * @return a list of controllers
     */
    List<? extends HttpCtlr> makeControllers(ImmutableHierarchicalConfiguration c);

    List<? extends HttpActionDefinition> getActionDefinitions();

    /**
     * Creates the application services.
     *
     * @param c configuration
     * @return list of java pojos.
     */
    List<?> makeServices(ImmutableHierarchicalConfiguration c);

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
