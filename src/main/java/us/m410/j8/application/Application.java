package us.m410.j8.application;

import com.google.common.collect.ImmutableList;
import us.m410.j8.controller.Controller;
import us.m410.j8.controller.ControllerComponent;
import us.m410.j8.orm.*;
import us.m410.j8.service.ServiceComponent;
import us.m410.j8.servlet.*;
import us.m410.j8.action.ActionDefinition;
import us.m410.j8.configuration.Configuration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 */
abstract public class Application
        implements LifeCycleComponent, ServiceComponent, ControllerComponent, JpaComponent,
        MigrationComponent, OrmBuilderComponent {

    private List<? extends ThreadLocalFactory> threadLocalsFactories;
    private List<?> services;
    private List<ActionDefinition> actionDefinitions;

    protected Configuration configuration;

    private Application() {}

    protected Application(Configuration config) {
        configuration = config;
    }

    public List<? extends ListenerDefinition> listeners() {
        return ImmutableList.of();
    }

    public List<? extends FilterDefinition> filters() {
        return ImmutableList.of(
                new FilterDefinitionDefault("m410", "us.m410.j8.servlet.Filter", "", "/*")
        );
    }

    public List<? extends ServletDefinition> servlets() {
        return ImmutableList.of(
                new ServletDefinitionDefault("std", "us.m410.j8.servlet.StandardServlet", "", ".j8std"),
                new ServletDefinitionDefault("async", "us.m410.j8.servlet.AsyncServlet", "", ".j8async"),
                new ServletDefinitionDefault("ws", "us.m410.j8.servlet.WebSocketServlet", "", ".j8ws")
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

    /**
     * Only gets called if the action is found by the filter and forwarded to this application.
     * @param req the http servlet request
     * @param res the http servlet response
     */
    public void doRequest(HttpServletRequest req, HttpServletResponse res) {
        actionDefinitions.stream().filter((a) -> a.doesPathMatch(req))
                .findFirst().get().apply(req, res);
    }

    public Optional<ActionDefinition> actionForRequest(HttpServletRequest request) {
        return Optional.ofNullable(null); // todo implement me
    }

    public interface Work { void doWork() throws IOException, ServletException;}

    public void doWithThreadLocals(Work work) throws IOException, ServletException{
        doWithThreadLocal(threadLocalsFactories,work);
    }

    protected void doWithThreadLocal(List<? extends ThreadLocalFactory> tlf, Work block)
            throws IOException, ServletException {
        if(tlf.size() >= 1) {
            ThreadLocal tl = tlf.get(tlf.size()-1).make();
            doWithThreadLocal(tlf.subList(0,tlf.size()-1), block);
            tl.remove();
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
        services.stream().forEach(s->{
            if(s instanceof LifeCycleComponent)
                ((LifeCycleComponent)s).onStartup();
        });
        ImmutableList.Builder<ActionDefinition> b = ImmutableList.builder();
        controllers.stream().forEach((c)->b.addAll(c.actions()));
        actionDefinitions = b.build();
    }

    public void onShutdown() {
        services.stream().forEach(s->{
            if(s instanceof LifeCycleComponent)
                ((LifeCycleComponent)s).onShutdown();
        });
        threadLocalsFactories.stream().forEach((tlf)->tlf.shutdown());
    }
}
