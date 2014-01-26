package org.m410.garden.application;

import org.m410.garden.configuration.ServletContextAppFactory;

import javax.servlet.*;

/**
 * This listener is setup in the web.xml and initializes the application.
 *
 * @author Michael Fortin
 */
public class ApplicationContextListener implements ServletContextListener {

    public static final String SCOPE_NAME = "application";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        // todo needs to be set dynamically
        String env = servletContext.getInitParameter("m410-env");

        ApplicationModule app = ServletContextAppFactory.forEnvironment(env);
        servletContext.setAttribute(SCOPE_NAME, app);

        app.getListeners().stream().forEach((l) -> {
            // need to know what type of listener so it can be proxied
            servletContext.addListener(l.getClassName());
        });

        app.getFilters().stream().forEach((s) -> {
            FilterRegistration.Dynamic d = servletContext.addFilter(s.getName(), s.getClassName());
            d.addMappingForUrlPatterns(s.dispatchTypes(), s.matchAfter(), s.urlPatterns());
        });

        app.getServlets().stream().forEach((s) -> {
            ServletRegistration.Dynamic d = servletContext.addServlet(s.getName(), s.getClassName());
            d.addMapping(s.mappings());
        });

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        Application a = (Application) servletContextEvent.getServletContext().getAttribute(SCOPE_NAME);

        if (a != null)
            a.destroy();

    }
}
