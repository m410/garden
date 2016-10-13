package org.m410.garden.fixtures;


import com.google.common.collect.ImmutableList;
import org.m410.garden.application.GardenApplication;
import org.m410.garden.di.ComponentSupplier;
import org.m410.garden.di.Components;
import org.m410.garden.di.ControllerSupplier;
import org.m410.garden.module.jpa.JpaModule;

import static org.m410.garden.di.ComponentBuilder.builder;


/**
 */
public class MyWebApp extends GardenApplication implements JpaModule {

    @Override
    public ComponentSupplier componentProvider() {
        return (components, config) -> Components.init()
                .add(() -> ImmutableList.of(
                        builder(MyServiceDao.class).factory((a, b) -> new MyServiceDaoImpl()),
                        builder(MyService.class).dependsOn(MyServiceDao.class).factory((a, b) -> new MyServiceImpl(
                                (MyServiceDao) b[0]))
                                           ));
    }

    @Override
    public ControllerSupplier controllerProvider() {
        return (components, config) -> ImmutableList.of(new MyController(config.typeOf(MyService.class)));
    }

    //    @Override
    //    public List<? extends EntityFactory> entityBuilders() {
    //        return ImmutableList.of();
    //    }
}

