package org.m410.garden.di.app;

import com.google.common.collect.ImmutableList;
import org.m410.garden.application.Application;
import org.m410.garden.configuration.Configuration;
import org.m410.garden.controller.HttpCtrl;
import org.m410.garden.di.Components;
import org.m410.garden.di.app.sample.SampleComponent;
import org.m410.garden.fixtures.MyController;
import org.m410.garden.fixtures.MyService;

import java.util.List;

/**
 * @author Michael Fortin
 */
public class SampleApplication extends Application {

    Components components = Components.init()
            .withProxy(new TransactionInvocationHandlerFactory())
            .withComponents(new SampleComponent())
            .make();

    @Override
    public List<? extends HttpCtrl> makeControllers(Configuration c) {
        return ImmutableList.of(
                new MyController(components.typeOf(MyService.class))
       );
    }
}
