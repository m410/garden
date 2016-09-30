package org.m410.garden.zone.transactional.fixtures;

import com.google.common.collect.ImmutableList;
import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;
import org.m410.garden.application.Application;
import org.m410.garden.controller.HttpCtlr;
import org.m410.garden.di.*;
import org.m410.garden.zone.ZoneFactory;
import org.m410.garden.zone.ZoneFactorySupplier;

import java.util.List;

import static org.m410.garden.di.ComponentBuilder.*;

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
        return (zoneManager, configuration) -> Components.init().add(component());
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
