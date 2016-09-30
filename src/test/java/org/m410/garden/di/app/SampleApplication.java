package org.m410.garden.di.app;

import com.google.common.collect.ImmutableList;
import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;
import org.m410.garden.application.Application;
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

    @Override
    public ControllerSupplier controllerProvider() {
        return (threadlocals, components) -> ImmutableList.of(
                new MyController(components.typeOf(MyService.class))
        );
    }

    protected ComponentSupplier componentsSupplier() {
        return (zoneManager, configuration) -> Components.init()
                .withProxy(zoneManager.getZoneFactories().get(0).zoneHandlerFactory())
                .add(new SampleComponent())
                .make();
    }
}
