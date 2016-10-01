package org.m410.garden.zone.transactional.fixtures;

import com.google.common.collect.ImmutableList;
import org.m410.garden.application.Application;
import org.m410.garden.di.*;
import org.m410.garden.zone.ZoneFactorySupplier;

import static org.m410.garden.di.ComponentBuilder.builder;

/**
 * @author m410
 */
public class TrxApplication extends Application {

    @Override
    public ZoneFactorySupplier zoneFactoryProvider() {
        return (config) -> ImmutableList.of(new TrxThreadLocalFactory());
    }

    @Override
    public ComponentSupplier componentProvider() {
        return (zoneManager, configuration) -> Components.init()
                .add(component())
                .withZoneHandler(zoneManager.byName("TrxThreadLocalFactory").zoneHandlerFactory());
    }

    private Component component() {
        return () -> ImmutableList.of(builder(MyService.class).factory(factory()));
    }

    private ComponentFactory<MyService> factory() {
        return (zoneFactory, dependencies) -> zoneFactory.proxy(MyService.class, new MyServiceImpl());
    }

    @Override
    public ControllerSupplier controllerProvider() {
        return (config,components) -> ImmutableList.of(new TrxController(components.typeOf(MyService.class)));
    }
}
