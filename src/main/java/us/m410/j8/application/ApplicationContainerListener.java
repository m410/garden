package us.m410.j8.application;

import us.m410.j8.configuration.ServletContextAppFactory;

import javax.servlet.*;
import java.util.EnumSet;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class ApplicationContainerListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        String env = servletContext.getInitParameter("brzy-env");
        Application app = ServletContextAppFactory.forEnvironment(env);
        servletContext.setAttribute("application", app);
        app.onStartup();

        app.listeners().stream().forEach((l) -> {
            servletContext.addListener(l.getClassName());
        });

        app.filters().stream().forEach((s) -> {
            // todo implement me
        });

        app.servlets().stream().forEach((s) -> {
            // todo implement me
        });

        FilterRegistration.Dynamic filter = servletContext.addFilter("BrzyFilter", "org.brzy.webapp.BrzyFilter");
        EnumSet dispatchTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);
        filter.addMappingForUrlPatterns(dispatchTypes, true, "/*");

        ServletRegistration.Dynamic main = servletContext.addServlet("BrzyServlet", "org.brzy.webapp.BrzyServlet");
        main.addMapping("*.brzy");

        ServletRegistration.Dynamic async = servletContext.addServlet("BrzyAsyncServlet", "org.brzy.webapp.BrzyAsyncServlet");
        async.setAsyncSupported(true);
        async.addMapping("*.brzy_async");

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        Application a = (Application) servletContextEvent.getServletContext().getAttribute("application");

        if (a != null)
            a.onShutdown();

    }
}
