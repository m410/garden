package org.m410.j8.application;

import org.m410.j8.action.ActionDefinition;
import org.m410.j8.configuration.Configuration;
import org.m410.j8.controller.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.ImmutableList;
import org.m410.j8.servlet.FilterDefinition;
import org.m410.j8.servlet.ListenerDefinition;
import org.m410.j8.servlet.ServletDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This is the base implementation of the applicationModule.  It's what uses
 * of the framework need to extend.
 *
 * @see ApplicationModule
 * @author Michael Fortin
 */
abstract public class Application implements ApplicationModule {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    private List<? extends ThreadLocalSessionFactory> threadLocalsFactories;
    private List<?> services;
    List<ActionDefinition> actionDefinitions;
    private List<ServletDefinition> servletDefinitions;
    private List<FilterDefinition> filterDefinitions;
    private List<ListenerDefinition> listenerDefinitions;

    @Override
    public List<ServletDefinition> getServlets() {
        return servletDefinitions;
    }

    @Override
    public List<FilterDefinition> getFilters() {
        return filterDefinitions;
    }


    @Override
    public List<ListenerDefinition> getListeners() {
        return listenerDefinitions;
    }

    // todo add other web.xml attributes to mvn build, like the orm config.

    /**
     * A listener implementation of one of the following.
     * <ul>
     * <li>javax.servlet.ServletRequestListener           </li>
     * <li>javax.servlet.ServletRequestAttributeListener  </li>
     * <li>javax.servlet.ServletContextListener           </li>
     * <li>javax.servlet.ServletContextAttributeListener  </li>
     * <li>javax.servlet.http.HttpSessionListener         </li>
     * <li>javax.servlet.http.HttpSessionAttributeListener</li>
     * <li>javax.servlet.http.HttpSessionAttributeListener</li>
     * </ul>
     *
     * @param c configuration
     * @return a list of container listeners.
     */
    public List<ListenerDefinition> makeListeners(Configuration c) {
        return ImmutableList.of();
    }

    /**
     * Creates the filter definitions that are added to the container at startup.
     *
     * @param c configuration
     * @return a list of filter definitions.
     */
    public List<FilterDefinition> makeFilters(Configuration c) {
        return ImmutableList.of(
                new FilterDefinition("M410Filter", "org.m410.j8.servlet.M410Filter", "/*")
        );
    }

    /**
     * Creates the servlet definitions that are added to the container at startup.
     *
     * @param c configuration
     * @return a list of servlet definitions
     */
    public List<ServletDefinition> makeServlets(Configuration c) {
        return ImmutableList.of(
                new ServletDefinition("M410Servlet", "org.m410.j8.servlet.M410Servlet", "", "*.m410")
        );
    }

    /**
     * Creates the thread local factories that will wrap each action request.
     *
     * @param c configuration
     * @return a list of thread local factories.
     */
    @Override
    public List<? extends ThreadLocalSessionFactory> makeThreadLocalFactories(Configuration c) {
        return ImmutableList.of();
    }

    /**
     * Creates sevrice classes with the available configuration.  Some modules may add
     * services through this method.
     *
     * @param c configuration
     * @return a list of service classes.
     */
    @Override
    public List<?> makeServices(Configuration c) {
        return ImmutableList.of();
    }

    /**
     * Only gets called if the action is found by the filter and forwarded to this application.
     *
     * @param req the http servlet request
     * @param res the http servlet response
     */
    public void doRequest(HttpServletRequest req, HttpServletResponse res) {
        log.debug("method={}, req={}", req.getMethod(), req.getRequestURI());
        actionDefinitions.stream().filter((a) -> a.doesRequestMatchAction(req))
                .findFirst().ifPresent((action)-> action.apply(req, res));
    }

    public Optional<ActionDefinition> actionForRequest(HttpServletRequest request) {
        return actionDefinitions.stream()
                .filter((ad) -> ad.doesRequestMatchAction(request))
                .findFirst();
    }

    public interface Work {
        void doWork() throws IOException, ServletException;
    }

    public void doWithThreadLocals(Work work) throws IOException, ServletException {
        doWithThreadLocal(threadLocalsFactories, work);
    }

    protected void doWithThreadLocal(List<? extends ThreadLocalSessionFactory> tlf, Work block)
            throws IOException, ServletException {
        if (tlf != null && tlf.size() >= 1) {
            ThreadLocalSession session = tlf.get(tlf.size() - 1).make();
            session.start();
            doWithThreadLocal(tlf.subList(0, tlf.size() - 1), block);
            session.stop();
        }
        else {
            block.doWork();
        }
    }

    /**
     * Initializes the application with the application configuration defined in the
     * configuration.m410.yml file.
     *
     * It initialized the thread locals, servlets, servlet filters, container listeners,
     * services, controllers and actions, in that order.
     *
     * @param configuration the configuration.
     */
    public void init(Configuration configuration) {
        threadLocalsFactories = makeThreadLocalFactories(configuration);
        log.debug("threadLocalsFactories: {}", threadLocalsFactories);

        servletDefinitions = makeServlets(configuration);
        log.debug("servletDefinitions: {}", servletDefinitions);

        filterDefinitions = makeFilters(configuration);
        log.debug("filterDefinitions: {}", filterDefinitions);

        listenerDefinitions = makeListeners(configuration);
        log.debug("listenerDefinitions: {}", listenerDefinitions);

        services = makeServices(configuration);
        log.debug("services: {}", services);

        List<? extends Controller> controllers = makeControllers(configuration);
        log.debug("controllers: {}", controllers);

        ImmutableList.Builder<ActionDefinition> b = ImmutableList.builder();
        controllers.stream().forEach((c) -> b.addAll(c.actions()));
        actionDefinitions = b.build();
        log.debug("actionDefinitions: {}", actionDefinitions);

    }

    public void destroy() {
        threadLocalsFactories.stream().forEach((tlf) -> tlf.shutdown());
    }
}
