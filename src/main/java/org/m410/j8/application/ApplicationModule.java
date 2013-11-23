package org.m410.j8.application;

import org.m410.j8.configuration.Configuration;
import org.m410.j8.controller.Controller;
import org.m410.j8.servlet.FilterDefinition;
import org.m410.j8.servlet.ListenerDefinition;
import org.m410.j8.servlet.ServletDefinition;

import java.util.List;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public interface ApplicationModule {

    List<ListenerDefinition> makeListeners(Configuration c);

    List<ListenerDefinition> getListeners();

    List<FilterDefinition> makeFilters(Configuration c) ;

    List<FilterDefinition> getFilters();

    List<ServletDefinition> makeServlets(Configuration c) ;

    List<ServletDefinition> getServlets();

    List<? extends ThreadLocalSessionFactory> makeThreadLocalFactories(Configuration c);

    List<? extends Controller> makeControllers(Configuration c);

    List<?> makeServices(Configuration c);

    void init(Configuration configuration);

    void destroy();
}
