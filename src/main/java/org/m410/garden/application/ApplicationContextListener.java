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

        app.getListeners().stream().forEach((l) -> l.configure(servletContext));
        app.getFilters().stream().forEach((s) -> s.configure(servletContext));
        app.getServlets().stream().forEach((s) -> s.configure(servletContext));
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        Application a = (Application) servletContextEvent.getServletContext().getAttribute(SCOPE_NAME);

        if (a != null)
            a.destroy();

    }
}
