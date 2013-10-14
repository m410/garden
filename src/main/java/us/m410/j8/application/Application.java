package us.m410.j8.application;

import us.m410.j8.action.ActionDefinition;
import us.m410.j8.configuration.Configuration;
import us.m410.j8.controller.Controller;
import us.m410.j8.controller.ControllerComponent;
import us.m410.j8.orm.*;
import us.m410.j8.service.ServiceComponent;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.ServerEndpointConfig;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.ImmutableList;
import us.m410.j8.servlet.FilterDefinition;
import us.m410.j8.servlet.ListenerDefinition;
import us.m410.j8.servlet.ServletDefinition;


/**
 */
abstract public class Application
        implements LifeCycleComponent, ServiceComponent, ControllerComponent, JpaComponent,
        MigrationComponent, OrmBuilderComponent {

    private List<? extends ThreadLocalFactory> threadLocalsFactories;
    private List<?> services;
    List<ActionDefinition> actionDefinitions;

    protected Configuration configuration;

    private Application() {
    }

    protected Application(Configuration config) {
        configuration = config;
    }

    public List<? extends ListenerDefinition> listeners() {
        return ImmutableList.of();
    }

    public List<? extends FilterDefinition> filters() {
        return ImmutableList.of(
                new FilterDefinition("M410Filter", "us.m410.j8.servlet.m410Filter", "/*")
        );
    }

    public List<? extends ServletDefinition> servlets() {
        return ImmutableList.of(
                new ServletDefinition("M410Servlet", "us.m410.j8.servlet.M410Servlet", "", ".m410")
        );
    }

    @Override
    public List<? extends OrmGenerator> ormGenerators() {
        return ImmutableList.of();
    }

    @Override
    public List<?> makeServices(Configuration c) {
        return ImmutableList.of();
    }

    @Override
    public List<? extends ThreadLocalFactory> threadLocalFactories(Configuration c) {
        return ImmutableList.of();
    }

//    public void addEndPoint() {
//        ServerEndpointConfig.Builder.create(getClass(),"").build();
//    }

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
        final Optional<ActionDefinition> optional = actionDefinitions.stream()
                .filter((ad) -> {
                    return ad.doesRequestMatchAction(request);
                })
                .findFirst();
        return optional;
    }

    public interface Work {
        void doWork() throws IOException, ServletException;
    }

    public void doWithThreadLocals(Work work) throws IOException, ServletException {
        doWithThreadLocal(threadLocalsFactories, work);
    }

    protected void doWithThreadLocal(List<? extends ThreadLocalFactory> tlf, Work block)
            throws IOException, ServletException {
        if (tlf.size() >= 1) {
            SessionStartStop session = tlf.get(tlf.size() - 1).make();
            session.start();
            doWithThreadLocal(tlf.subList(0, tlf.size() - 1), block);
            session.stop();
        }
        else {
            block.doWork();
        }
    }

    public void onStartup() {
        doMigration(configuration);
        threadLocalsFactories = threadLocalFactories(configuration);
        services = makeServices(configuration);
        List<? extends Controller> controllers = makeControllers(configuration);
        services.stream().forEach(s -> {
            if (s instanceof LifeCycleComponent)
                ((LifeCycleComponent) s).onStartup();
        });
        ImmutableList.Builder<ActionDefinition> b = ImmutableList.builder();
        controllers.stream().forEach((c) -> b.addAll(c.actions()));
        actionDefinitions = b.build();
    }

    public void onShutdown() {
        services.stream().forEach(s -> {
            if (s instanceof LifeCycleComponent)
                ((LifeCycleComponent) s).onShutdown();
        });
        threadLocalsFactories.stream().forEach((tlf) -> tlf.shutdown());
    }
}
