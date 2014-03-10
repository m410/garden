package org.m410.garden.application;

import org.m410.garden.controller.HttpCtrl;
import org.m410.garden.controller.action.ActionDefinition;
import org.m410.garden.configuration.Configuration;
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
    List<ListenerDefinition> makeListeners(Configuration c);

    List<ListenerDefinition> getListeners();

    /**
     * creates a list of servlet filters that are added to the servlet
     * container at startup.
     *
     * @param c configuration
     * @return list of filter definitions
     */
    List<FilterDefinition> makeFilters(Configuration c) ;

    List<FilterDefinition> getFilters();

    /**
     * creates a list of servlet definitions that are added to the servlet
     * container at startup.
     *
     * @param c configuration
     * @return a list of servlet definitions
     */
    List<ServletDefinition> makeServlets(Configuration c) ;

    List<ServletDefinition> getServlets();

    /**
     * Creates the threadLocal factories for the application.
     *
     * @param c configuration
     * @return a list of thread local factory implementations.
     */
    List<? extends ThreadLocalSessionFactory> makeThreadLocalFactories(Configuration c);

    List<? extends ThreadLocalSessionFactory> getThreadLocalFactories();

    /**
     * Creates the controllers for the application.
     *
     * @param c configuration
     * @return a list of controllers
     */
    List<? extends HttpCtrl> makeControllers(Configuration c);

    List<? extends ActionDefinition> getActionDefinitions();

    /**
     * Creates the application services.
     *
     * @param c configuration
     * @return list of java pojos.
     */
    List<?> makeServices(Configuration c);

    /**
     * This is the main initialization method for the application.  It will be
     * called by the {@link ApplicationContextListener} on startup.  This, in turn,
     * calls the other factory methods using the passed in configuration.
     *
     * @param configuration configuration
     */
    void init(Configuration configuration);

    void destroy();
}
