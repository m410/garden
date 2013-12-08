package org.m410.j8.application;

import org.m410.j8.action.ActionDefinition;
import org.m410.j8.configuration.Configuration;
import org.m410.j8.controller.Ctlr;

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
 * This is the base implementation of the applicationModule.  All projects using
 * this framework must implement one class that extends this class.
 *
 * There is only one instance of an application per running web application.
 *
 * This class is created by the {@link org.m410.j8.application.ApplicationContextListener}
 * when the war is initialized and it is placed into the ServletContext's application scope
 * with by the name "application".  It can be accessible from even listeners and jsp's
 * by doing something like
 * <code>request.getServletContext().getAttribute("application")</code>.
 *
 * The application class and all it's properties are immutable, and any properties
 * added by a developer should also treat it as such.
 *
 * @see ApplicationModule
 * @author Michael Fortin
 */
abstract public class Application implements ApplicationModule {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    private List<? extends ThreadLocalSessionFactory> threadLocalsFactories;
    private List<?> services;
    private List<ActionDefinition> actionDefinitions;
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

    @Override
    public List<ActionDefinition> getActionDefinitions() {
        return actionDefinitions;
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
     * The most common example usage is if you would like to perform some action
     * on session start or session expire.  In those cases add your own implement
     * of the HttpSessionListener here.
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
     * If you want to add other servlets to the application you can add them here, for
     * example if you want to add Velocity for view rendering.
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
     * Creates the thread local factories that will wrap each action request.  In most cases
     * you will not need to override this unless more than one module adds thread locals
     * to the application.  In which case you will need to do something like
     *
     * <pre>
     *  public List&lt;...&gt; makeThreadLocalFactories(Configuration c) {
     *    return ImmutableList.Builder
     *          .addAll(JpaModule.super.makeThreadLocalFactories(c))
     *          .addAll(JmsModule.super.makeThreadLocalFactories(c))
     *          .build();
     *  }
     * </pre>
     *
     * @param c configuration
     * @return a list of thread local factories.
     */
    @Override
    public List<? extends ThreadLocalSessionFactory> makeThreadLocalFactories(Configuration c) {
        return ImmutableList.of();
    }

    /**
     * Creates service classes with the available configuration.  Some modules may add
     * services through this method.
     *
     * It is not explicitly necessary for you to add your service here unless you
     * require lifecycle management.  Note lifecycle management is not fully implemented yet.
     *
     * @see org.m410.j8.application.ApplicationModule#makeControllers(org.m410.j8.configuration.Configuration)
     * @param c configuration
     * @return a list of service classes.
     */
    @Override
    public List<?> makeServices(Configuration c) {
        return ImmutableList.of();
    }

    /**
     * This does the work of executing an action on a request.
     *
     * It only gets called if the action is found by the filter and forwarded to this
     * application.
     *
     * @param req the http servlet request
     * @param res the http servlet response
     */
    public void doRequest(HttpServletRequest req, HttpServletResponse res) {
        log.debug("method={}, req={}", req.getMethod(), req.getRequestURI());
        actionDefinitions.stream().filter((a) -> a.doesRequestMatchAction(req))
                .findFirst().ifPresent((action)-> action.apply(req, res));
    }

    /**
     * Finds an action based on the request URI.
     *
     * @param request the HttpServletException
     * @return Optional ActionDefinition
     */
    public Optional<ActionDefinition> actionForRequest(HttpServletRequest request) {
        return actionDefinitions.stream()
                .filter((ad) -> ad.doesRequestMatchAction(request))
                .findFirst();
    }

    /**
     * Used for internal use to wrap actions in a single method function.
     */
    public interface Work {
        void doWork();
    }

    /**
     * Wraps action invocations with a thread local context.
     *
     * @param work internal closure to wrap the action.
     */
    public void doWithThreadLocals(Work work) {
        doWithThreadLocal(threadLocalsFactories, work);
    }

    /**
     * This is part of the plumbing of the application that you shouldn't need to change.  It's
     * called by the application to wrap each request within a thread local context.
     *
     * @param tlf list of ThreadLocalFactory objects
     * @param block an internal worker closure.
     */
    protected void doWithThreadLocal(List<? extends ThreadLocalSessionFactory> tlf, Work block) {
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
     * You may want to add some initialization of your own by overriding this, and if
     * you do, be sure to call super.init(configuration).
     *
     * @see org.m410.j8.application.ApplicationModule#init(org.m410.j8.configuration.Configuration)
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

        List<Ctlr> controllers = makeControllers(configuration);
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
