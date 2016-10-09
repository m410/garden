package org.m410.garden.di.app;

import com.google.common.collect.ImmutableList;
import org.m410.garden.application.Application;
import org.m410.garden.di.ComponentSupplier;
import org.m410.garden.di.Components;
import org.m410.garden.di.ControllerSupplier;
import org.m410.garden.di.app.sample.SampleComponent;
import org.m410.garden.fixtures.MyController;
import org.m410.garden.fixtures.MyService;
import org.m410.garden.zone.*;

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

    @Override
    public ZoneFactorySupplier zoneFactoryProvider() {
        return (config) -> ImmutableList.of(new ZoneFactory() {

            @Override
            public void setZoneManager(ZoneManager zoneManager) {
            }

            @Override
            public String name() {
                return "test";
            }

            @Override
            public Zone makeZone() {
                return null;
            }

            @Override
            public void shutdown() {

            }

            @Override
            public ZoneHandlerFactory zoneHandlerFactory() {
                return new ZoneHandlerFactory() {
                    @Override
                    public <T> T proxy(Class<T> interfce, T instance) {
                        return instance;
                    }
                };
            }
        });
    }

    @Override
    public ComponentSupplier componentProvider() {
        return (zoneManager, configuration) -> Components.init()
                .with(zoneManager)
                .add(new SampleComponent())
                .make();
    }
}
