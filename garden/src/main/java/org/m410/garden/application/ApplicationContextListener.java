package org.m410.garden.application;

import org.m410.garden.configuration.ServletContextAppFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * This listener is setup in the web.xml and initializes the application.
 *
 * @author Michael Fortin
 */
public final class ApplicationContextListener implements ServletContextListener {

    private static final String SCOPE_NAME = "application";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        // todo needs to be set dynamically
        String env = servletContext.getInitParameter("m410-env");

        ApplicationModule app = ServletContextAppFactory.forEnvironment(env);
        servletContext.setAttribute(SCOPE_NAME, app);

        app.getListeners().forEach(listener -> listener.configure(servletContext));
        app.getFilters().forEach(filter -> filter.configure(servletContext));
        app.getServlets().forEach(servlet -> servlet.configure(servletContext));
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        GardenApplication a = (GardenApplication) servletContextEvent.getServletContext().getAttribute(SCOPE_NAME);

        if (a != null)
            a.destroy();

    }
}
