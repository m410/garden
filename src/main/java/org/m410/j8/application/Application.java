package org.m410.j8.application;

import org.m410.j8.action.ActionDefinition;
import org.m410.j8.configuration.Configuration;
import org.m410.j8.controller.Controller;
import org.m410.j8.persistence.*;

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
 */
abstract public class Application implements ApplicationComponent {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    private List<? extends ThreadLocalSessionFactory> threadLocalsFactories;
    private List<?> services;
    List<ActionDefinition> actionDefinitions;
    private List<ServletDefinition> servletDefinitions;
    private List<FilterDefinition> filterDefinitions;
    private List<ListenerDefinition> listenerDefinitions;

    /**
     *
     * @return
     */
    @Override
    public List<ServletDefinition> getServlets() {
        return servletDefinitions;
    }

    /**
     *
     * @return
     */
    @Override
    public List<FilterDefinition> getFilters() {
        return filterDefinitions;
    }

    /**
     *
     * @return
     */
    @Override
    public List<ListenerDefinition> getListeners() {
        return listenerDefinitions;
    }

    // todo add other web.xml attributes to mvn build, like the orm config.

    /**
     *
     * @param c
     * @return
     */
    public List<ListenerDefinition> makeListeners(Configuration c) {
        return ImmutableList.of();
    }

    /**
     *
     * @param c
     * @return
     */
    public List<FilterDefinition> makeFilters(Configuration c) {
        return ImmutableList.of(
                new FilterDefinition("M410Filter", "org.m410.j8.servlet.M410Filter", "/*")
        );
    }

    /**
     *
     * @param c
     * @return
     */
    public List<ServletDefinition> makeServlets(Configuration c) {
        return ImmutableList.of(
                new ServletDefinition("M410Servlet", "org.m410.j8.servlet.M410Servlet", "", "*.m410")
        );
    }

    /**
     *
     * @param c
     * @return
     */
    @Override
    public List<? extends ThreadLocalSessionFactory> makeThreadLocalFactories(Configuration c) {
        return ImmutableList.of();
    }

    /**
     *
     * @param c
     * @return
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
        actionDefinitions.stream().filter((a) -> a.doesRequestMatchAction(req))
                .findFirst().ifPresent((action)->action.apply(req, res));
    }

    public Optional<ActionDefinition> actionForRequest(HttpServletRequest request) {
        return actionDefinitions.stream()
                .filter((ad) -> {
                    return ad.doesRequestMatchAction(request);
                })
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
        if (tlf.size() >= 1) {
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
     *
     * @param configuration
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
