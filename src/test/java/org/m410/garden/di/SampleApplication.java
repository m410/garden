package org.m410.garden.di;

import com.google.common.collect.ImmutableList;
import org.m410.garden.application.Application;
import org.m410.garden.configuration.Configuration;
import org.m410.garden.controller.HttpCtrl;
import org.m410.garden.fixtures.MyController;
import org.m410.garden.fixtures.MyService;

import java.util.List;

/**
 * @author Michael Fortin
 */
public class SampleApplication extends Application {
    Components components = Components.init()
            .withTransaction(null)
            .withComponents(new SampleComponent())
            .make();

    MyService myService = components.typeOf(MyService.class);

    @Override public List<? extends HttpCtrl> makeControllers(Configuration c) {
        return ImmutableList.of(
                new MyController(myService)
       );
    }
}
