package org.m410.j8.application;

import org.m410.j8.controller.action.ActionDefinition;
import org.m410.j8.controller.action.http.HttpActionDefinition;
import org.m410.j8.configuration.Configuration;
import org.m410.j8.controller.Ctlr;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.ImmutableList;
import org.m410.j8.servlet.FilterDefinition;
import org.m410.j8.servlet.ListenerDefinition;
import org.m410.j8.servlet.ServletDefinition;
import org.m410.j8.transaction.ThreadLocalSession;
import org.m410.j8.transaction.ThreadLocalSessionFactory;
import org.m410.j8.transaction.TransactionHandler;
import org.m410.j8.transaction.TransactionScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This is the base implementation of the applicationModule.  All projects using
 * this framework must implement one class that extends this class.
 * <p>
 * There is only one instance of an application per running web application.
 * <p>
 * This class is created by the {@link org.m410.j8.application.ApplicationContextListener}
 * when the war is initialized and it is placed into the ServletContext's application scope
 * with by the name "application".  It can be accessible from even listeners and jsp's
 * by doing something like
 * <code>request.getServletContext().getAttribute("application")</code>.
 * <p>
 * The application class and all it's properties should be considered immutable, and
 * any properties added by a developer should also treat it as such for thread safety.
 *
 * @author Michael Fortin
 * @see ApplicationModule
 */
abstract public class Application implements ApplicationModule {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    private List<? extends ThreadLocalSessionFactory> threadLocalsFactories;
    private List<?> services;
    private List<? extends ActionDefinition> actionDefinitions;
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
    public List<? extends ActionDefinition> getActionDefinitions() {
        return actionDefinitions;
    }

    @Override
    public List<? extends ThreadLocalSessionFactory> getThreadLocalFactories() {
        return threadLocalsFactories;
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
     * <p>
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
     * <p>
     * If you want to add other servlets to the application you can add them here, for
     * example if you want to add Velocity for view rendering.
     * <p>
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
     * Creates service classes with the available configuration.  Some modules may add
     * services through this method.
     * <p>
     * It is not explicitly necessary for you to add your service here unless you
     * require lifecycle management.  Note lifecycle management is not fully implemented yet.
     *
     * @param c configuration
     * @return a list of service classes.
     * @see org.m410.j8.application.ApplicationModule#makeControllers(org.m410.j8.configuration.Configuration)
     */
    @Override
    public List<?> makeServices(Configuration c) {
        return ImmutableList.of();
    }

    /**
     * This does the work of executing an action on a request.
     * <p>
     * It only gets called if the action is found by the filter and forwarded to this
     * application.
     *
     * @param req the http servlet request
     * @param res the http servlet response
     */
    public void doRequest(HttpServletRequest req, HttpServletResponse res) {
        log.debug("method={}, req={}", req.getMethod(), req.getRequestURI());
        actionDefinitions.stream()
                .filter((a) -> a instanceof HttpActionDefinition)
                .filter((a) -> ((HttpActionDefinition)a).doesRequestMatchAction(req))
                .findFirst()
                .ifPresent((a) -> {
                    final HttpActionDefinition definition = (HttpActionDefinition) a;

                    if(definition.getTransactionScope() == TransactionScope.Action)
                        doWithThreadLocals(()->{
                            definition.apply(req, res);
                            return null;
                        });
                    else
                        definition.apply(req, res);

                }
            );
    }

    /**
     * Finds an action based on the request URI.
     *
     * @param request the HttpServletException
     * @return Optional ActionDefinition
     */
    public Optional<HttpActionDefinition> actionForRequest(HttpServletRequest request) {
        return actionDefinitions.stream()
                .filter((a) -> a instanceof HttpActionDefinition)
                .filter((a) -> ((HttpActionDefinition)a).doesRequestMatchAction(request))
                .map((a)->((HttpActionDefinition)a))
                .findFirst();
    }

    /**
     * Used for internal use to wrap actions in a single method function.
     */
    public interface Work {
        Object doWork();
    }

    /**
     * Wraps action invocations with a thread local context.
     *
     * @param work internal closure to wrap the action.
     * @return when it's called by the filter or an action this can and should return null, when
     *  it's called to wrap a service call, it should be the the result of the method invocation.
     */
    public Object doWithThreadLocals(Work work) {
        return doWithThreadLocal(threadLocalsFactories, work);
    }

    /**
     * This is part of the plumbing of the application that you shouldn't need to change.  It's
     * called by the application to wrap each request within a thread local context.
     *
     * @param tlf   list of ThreadLocalFactory objects
     * @param block an internal worker closure.
     * @return in most cases it will be null, except when wrapping the call to a service method.
     */
    protected Object doWithThreadLocal(List<? extends ThreadLocalSessionFactory> tlf, Work block) {
        if (tlf != null && tlf.size() >= 1) {
            ThreadLocalSession session = tlf.get(tlf.size() - 1).make();
            session.start();
            Object result = doWithThreadLocal(tlf.subList(0, tlf.size() - 1), block);
            session.stop();
            return result;
        }
        else {
            return block.doWork();
        }
    }

    /**
     * Wrap services in a transaction.  Actions do not have to be transactional, you have
     * the option to set the transaction boundries at the service level by wrapping its
     * declaration with this method.
     *
     * <pre>
     *     MyService myService = transactional(MyService.class,new MyServiceImpl());
     * </pre>
     *
     *
     * @param intrface the Class for the interface that is proxied.
     * @param instance an instance of the interface that is invoked.
     * @param methods names of method to wrap in a transaction, if all transaction are
     *                wrapped in a transaction, leave this empty.
     * @param <T> the type of the class and instance to proxy
     * @return a proxy for the instance.
     */
    protected <T> T transactional(Class<T> intrface, T instance, String... methods) {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        final Class[] interfaces = {intrface};
        final TransactionHandler<T> handler = new TransactionHandler<>(instance, methods, this);
        return (T)Proxy.newProxyInstance(loader, interfaces, handler);
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

        List<? extends Ctlr> controllers = makeControllers(configuration);
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
