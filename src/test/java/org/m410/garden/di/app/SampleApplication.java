package org.m410.garden.di.app;

import com.google.common.collect.ImmutableList;
import org.m410.garden.application.Application;
import org.m410.garden.configuration.Configuration;
import org.m410.garden.controller.HttpCtlr;
import org.m410.garden.di.ComponentSupplier;
import org.m410.garden.di.Components;
import org.m410.garden.di.ControllerSupplier;
import org.m410.garden.di.app.sample.SampleComponent;
import org.m410.garden.fixtures.MyController;
import org.m410.garden.fixtures.MyService;

import java.util.List;

/**
 * @author Michael Fortin
 */
public class SampleApplication extends Application {

    Components components = Components.init()
            .withProxy(new LogInvocationHandlerFactory())
            .withComponents(new SampleComponent())
            .make();

    @Override
    public List<? extends HttpCtlr> makeControllers(Configuration c) {
        return ImmutableList.of(
                new MyController(components.typeOf(MyService.class))
       );
    }

    // todo I like this design pattern better
    ComponentSupplier componentsSupplier() {
        return (invocationHandlerFactory, configuration) -> Components.init()
                .withProxy(invocationHandlerFactory)
                .withComponents(new SampleComponent())
                .make();
    }

    ControllerSupplier controllerSupplier() {
        return (configuration, components) -> ImmutableList.of(
                new MyController(components.typeOf(MyService.class)));
    }

}
