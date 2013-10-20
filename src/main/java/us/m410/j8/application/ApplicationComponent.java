package us.m410.j8.application;

import us.m410.j8.configuration.Configuration;
import us.m410.j8.controller.Controller;
import us.m410.j8.persistence.ThreadLocalFactory;
import us.m410.j8.servlet.FilterDefinition;
import us.m410.j8.servlet.ListenerDefinition;
import us.m410.j8.servlet.ServletDefinition;

import java.util.List;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public interface ApplicationComponent {

    List<ListenerDefinition> makeListeners(Configuration c);

    List<ListenerDefinition> getListeners();

    List<FilterDefinition> makeFilters(Configuration c) ;

    List<FilterDefinition> getFilters();

    List<ServletDefinition> makeServlets(Configuration c) ;

    List<ServletDefinition> getServlets();

    List<? extends ThreadLocalFactory> makeThreadLocalFactories(Configuration c);

    List<? extends Controller> makeControllers(Configuration c);

    List<?> makeServices(Configuration c);

    void init(Configuration configuration);

    void destroy();
}
