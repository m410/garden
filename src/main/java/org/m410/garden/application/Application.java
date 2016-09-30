package org.m410.garden.application;

import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;
import org.m410.garden.application.annotate.*;
import org.m410.garden.controller.HttpCtlr;
import org.m410.garden.controller.action.http.HttpActionDefinition;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableList;
import org.m410.garden.di.*;
import org.m410.garden.servlet.FilterDefinition;
import org.m410.garden.servlet.ListenerDefinition;
import org.m410.garden.servlet.ServletDefinition;
import org.m410.garden.zone.ZoneFactory;
import org.m410.garden.zone.ZoneManager;
import org.m410.garden.zone.transaction.TransactionScope;
import org.m410.garden.zone.ZoneFactorySupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This is the base implementation of the applicationModule.  All projects using
 * this framework must implement one class that extends this class.
 * <p>
 * There is only one instance of an application per running web application.
 * <p>
 * This class is created by the {@link org.m410.garden.application.ApplicationContextListener}
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

    private Components components;
    private List<? extends HttpCtlr> controllers;
    private List<HttpActionDefinition> actionDefinitions;
    private List<ServletDefinition> servletDefinitions;
    private List<FilterDefinition> filterDefinitions;
    private List<ListenerDefinition> listenerDefinitions;
    private ZoneManager zoneManager;


    // todo add other web.xml attributes to mvn build, like the orm config.

    //  todo add error routes by content type
    //  public void errorRouting(Router router) {
    //      router.for("application.json", 404).view("/_/errors/404.json")
    //          .for("application/json", 500).view("/_/errors/500.json");
    //  }


    public Components getComponents() {
        return components;
    }

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
    public List<HttpActionDefinition> getActionDefinitions() {
        return actionDefinitions;
    }

    public ZoneManager getZoneManager() {
        return zoneManager;
    }

    /**
     * Creates the filter definitions that are added to the container at startup.
     *  <p>
     * If you want to add other servlet filters to the application you can add them by overriding
     * the filterProvider() method, this one can not be overridden.
     * <p>
     *
     * @return a list of filter definitions.
     */
    @FilterProvider
    static FilterSupplier gardenFilterProvider() {
        return (config) -> ImmutableList.of(
                new FilterDefinition("M410Filter", "org.m410.garden.servlet.M410Filter", "/*")
        );
    }

    /**
     * Creates the servlet definitions that are added to the container at startup.
     * <p>
     * If you want to add other servlets to the application you can add them by overriding
     * the servletProvider() method, this one can not be overridden.
     * <p>
     *
     * @return a list of servlet definitions
     */
    @ServletProvider
    static ServletSupplier gardenServletProvider() {
       return (config) -> ImmutableList.of(
                new ServletDefinition("M410Servlet", "org.m410.garden.servlet.M410Servlet", "", "*.m410")
       );
    }

    public ServletSupplier servletProvider() {
        return (config) -> ImmutableList.of();
    }

    public FilterSupplier filterProvider() {
        return (config) -> ImmutableList.of();
    }

    public ListenerSupplier listenerProvider() {
        return (config) -> ImmutableList.of();
    }

    /**
     * Creates service classes with the available configuration.  Some modules may add
     * services through this method.
     * <p>
     * It is not explicitly necessary for you to add your service here unless you
     * require lifecycle management.  Note lifecycle management is not fully implemented yet.
     *
     * @return a list of service classes.
     */
    public ComponentSupplier componentProvider() {
        return (components, config) -> Components.init();
    }

    public ControllerSupplier controllerProvider() {
        return (components, config) -> ImmutableList.of();
    }

    public ZoneFactorySupplier zoneFactoryProvider() {
        return (config) -> ImmutableList.of();
    }


    /**
     * This does the work of executing an action on a request.
     * <p>
     * It only gets called if the action is found by the filter and forwarded to this
     * application.
     *
     * @param req the http servlet request
     * @param res the http servlet response
     * @throws Exception everything by default.
     */
    public final void doRequest(HttpServletRequest req, HttpServletResponse res) throws Exception {
        log.debug("method={}, req={}", req.getMethod(), req.getRequestURI());
        Optional optional = actionDefinitions.stream()
                .filter(action -> action.doesMatchRequest(req))
                .findFirst();

        if(optional.isPresent()) {
            HttpActionDefinition definition = (HttpActionDefinition)optional.get();

            if (definition.getTransactionScope() == TransactionScope.Action)
                zoneManager.doInZone(() -> {
                    definition.apply(req, res);
                    return null;
                });
            else
                definition.apply(req, res);
        }
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
                .filter((a) -> ((HttpActionDefinition) a).doesMatchRequest(request))
                .map((a) -> ((HttpActionDefinition) a))
                .findFirst();
    }

    /**
     * Initializes the application with the application configuration defined in the
     * garden.fab.yml file.
     *
     * It initialized the thread locals, servlets, servlet filters, container listeners,
     * services, controllers and actions, in that order.
     *
     * You may want to add some initialization of your own by overriding this, and if
     * you do, be sure to call super.init(configuration).
     *
     * @see ApplicationModule#init(ImmutableHierarchicalConfiguration)
     * @param configuration the configuration.
     */
    public void init(final ImmutableHierarchicalConfiguration configuration) {
        final Collection<? extends ZoneFactory> locals = dynamicProviders(ThreadLocalProvider.class, ZoneFactorySupplier.class)
                .map(s -> s.get(configuration))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        final ImmutableList<ZoneFactory> zoneFactories = ImmutableList.<ZoneFactory>builder()
                .addAll(locals)
                .addAll(zoneFactoryProvider().get(configuration))
                .build();
        zoneManager = new ZoneManager(zoneFactories);
        log.debug("zoneFactories: {}", zoneFactories);

        final List<ServletDefinition> servlets = dynamicProviders(ServletProvider.class, ServletSupplier.class)
                .map(s -> s.get(configuration))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        servletDefinitions = ImmutableList.<ServletDefinition>builder()
                .addAll(servlets)
                .addAll(servletProvider().get(configuration))
                .build();
        log.debug("servletDefinitions: {}", servletDefinitions);


        final List<FilterDefinition> filters = dynamicProviders(FilterProvider.class, FilterSupplier.class)
                .map(s -> s.get(configuration))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        filterDefinitions = ImmutableList.<FilterDefinition>builder()
                .addAll(filters)
                .addAll(filterProvider().get(configuration))
                .build();
        log.debug("filterDefinitions: {}", filterDefinitions);


        final List<ListenerDefinition> listeners = dynamicProviders(ListenerProvider.class, ListenerSupplier.class)
                .map(s -> s.get(configuration))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        listenerDefinitions = ImmutableList.<ListenerDefinition>builder()
                .addAll(listeners)
                .addAll(listenerProvider().get(configuration))
                .build();
        log.debug("listenerDefinitions: {}", listenerDefinitions);

        final Components dyComponents = dynamicProviders(ComponentsProvider.class, ComponentSupplier.class)
                .map(s -> s.get(zoneManager, configuration))
                .reduce(Components.init(), (a,b)-> b.inherit(a.make()));

         components = componentProvider().get(zoneManager, configuration)
                .inherit(dyComponents.make())
                .make();
        log.debug("components: {}", components);

        final List<? extends HttpCtlr> ctlrs = dynamicProviders(ControllerProvider.class, ControllerSupplier.class)
                .map(s -> s.get(configuration, components))
                .flatMap(Collection::stream)
                .collect(Collectors.collectingAndThen(Collectors.toList(), ImmutableList::copyOf));

        controllers = ImmutableList.<HttpCtlr>builder()
                .addAll(ctlrs)
                .addAll(controllerProvider().get(configuration, components))
                .build();
        log.debug("controllers: {}", controllers);

        actionDefinitions = controllers.stream()
                .map(HttpCtlr::actions)
                .flatMap(Collection::stream)
                .collect(Collectors.collectingAndThen(Collectors.toList(), ImmutableList::copyOf));

        log.debug("actionDefinitions: {}", actionDefinitions);

        callDynamicStart(configuration, Startup.class);
    }

    private <M> Stream<M> dynamicProviders(Class annotatedProvider, Class<M> clzz) {
        return Arrays.stream(getClass().getDeclaredMethods())
                .filter(method -> isAnnotatedWith(annotatedProvider, method))
                .map(method -> {
                    try {
                        final Object invoked = ((Method)method).invoke(null);
                        return (M) invoked;
                    }
                    catch (IllegalAccessException  | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });
    };

    private <T> boolean isAnnotatedWith(Class<T> componentClass, Method m) {
        return Arrays.stream(m.getDeclaredAnnotations())
                .filter(a -> a.annotationType().equals(componentClass))
                .findAny()
                .isPresent();
    }

    private <T> void callDynamicStart(ImmutableHierarchicalConfiguration configuration, Class<T> componentClass) {
        final Class thisClass = getClass();
        Arrays.stream(thisClass.getMethods())
                .filter(m -> isAnnotatedWith(componentClass,m))
                .forEach(component -> invokeMethod(configuration, component));
    }

    private void invokeMethod(ImmutableHierarchicalConfiguration configuration, Method component) {
        try {
            component.invoke(this, configuration);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void destroy() {
        zoneManager.getZoneFactories().forEach(ZoneFactory::shutdown);
        final Class thisClass = getClass();

        Arrays.stream(thisClass.getMethods())
                .filter(this::doesImplementShutdown)
                .forEach(this::invokeShutdown);
    }

    private boolean doesImplementShutdown(Method m) {
        return Arrays.stream(m.getDeclaredAnnotations())
                .filter(a -> a.annotationType().equals(Shutdown.class))
                .findAny().isPresent();
    }

    private void invokeShutdown(Method shutdownMethod) {
        try {
            shutdownMethod.invoke(this);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Used for internal use to wrap actions in a single method function.  It's simply a Supplier
     * with throws clause.
     */
    @FunctionalInterface
    public interface Work {
        Object get() throws Exception;
    }
}
