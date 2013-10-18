package us.m410.j8.application;

import us.m410.j8.configuration.ServletContextAppFactory;

import javax.servlet.*;
import java.util.EnumSet;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class ApplicationContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        String env = "development";// servletContext.getInitParameter("m410-env");
        Application app = ServletContextAppFactory.forEnvironment(env);
        servletContext.setAttribute("application", app);

        app.listeners().stream().forEach((l) -> {
            // need to know what type of listener so it can be proxied
            servletContext.addListener(l.getClassName());
        });

        app.filters().stream().forEach((s) -> {
            FilterRegistration.Dynamic d = servletContext.addFilter(s.getName(), s.getClassName());
            d.addMappingForUrlPatterns(s.dispatchTypes(), s.matchAfter(), s.urlPatterns());
        });

        app.servlets().stream().forEach((s) -> {
            ServletRegistration.Dynamic d = servletContext.addServlet(s.getName(), s.getClassName());
            d.addMapping(s.mappings());
        });

        app.onStartup();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        Application a = (Application) servletContextEvent.getServletContext().getAttribute("application");

        if (a != null)
            a.onShutdown();

    }
}
