package org.m410.garden.fixtures;


import com.google.common.collect.ImmutableList;
import org.m410.garden.application.Application;
import org.m410.garden.di.ComponentSupplier;
import org.m410.garden.di.Components;
import org.m410.garden.di.ControllerSupplier;
import org.m410.garden.module.jms.JmsModule;
import org.m410.garden.module.mail.MailModule;
import org.m410.garden.module.migration.MigrationModule;
import org.m410.garden.module.ormbuilder.OrmBuilderModule;
import org.m410.garden.module.ormbuilder.orm.EntityFactory;

import java.util.List;

import static org.m410.garden.di.ComponentBuilder.builder;


/**
 */
public class MyWebApp extends Application implements MailModule, JmsModule, OrmBuilderModule, MigrationModule {

    @Override
    public ComponentSupplier componentProvider() {
        return (components, config) -> Components.init()
                .add(() -> ImmutableList.of(
                        builder(MyServiceDao.class).factory((a, b) -> new MyServiceDaoImpl()),
                        builder(MyService.class).factory((a, b) -> new MyServiceImpl((MyServiceDao) b[0]))
                                           ));

    }

    @Override
    public ControllerSupplier controllerProvider() {
        return (components, config) -> ImmutableList.of(new MyController(config.typeOf(MyService.class)));
    }

    @Override
    public List<? extends EntityFactory> entityBuilders() {
        return ImmutableList.of();
    }
}

