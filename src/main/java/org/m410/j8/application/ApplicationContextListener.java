package org.m410.j8.application;

import org.m410.j8.configuration.ServletContextAppFactory;

import javax.servlet.*;

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
        ApplicationComponent app = ServletContextAppFactory.forEnvironment(env);
        servletContext.setAttribute("application", app);

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
        Application a = (Application) servletContextEvent.getServletContext().getAttribute("application");

        if (a != null)
            a.destroy();

    }
}
