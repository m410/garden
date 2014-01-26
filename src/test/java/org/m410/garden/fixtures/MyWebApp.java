package org.m410.garden.fixtures;


import com.google.common.collect.ImmutableList;
import org.m410.garden.application.Application;
import org.m410.garden.configuration.Configuration;
import org.m410.garden.controller.Ctlr;
import org.m410.garden.module.migration.MigrationModule;
import org.m410.garden.module.jpa.JpaModule;
import org.m410.garden.module.ormbuilder.OrmBuilderModule;
import org.m410.garden.module.ormbuilder.orm.EntityFactory;
import org.m410.garden.module.jms.JmsModule;
import org.m410.garden.module.mail.MailModule;

import java.util.List;


/**
 */
public class MyWebApp extends Application implements JpaModule, MailModule, JmsModule,
        OrmBuilderModule, MigrationModule {


    MyServiceDao myServiceDao = new MyServiceDaoImpl();
    MyService myService = new MyServiceImpl(myServiceDao);

    @Override
    public List<?> makeServices(Configuration c) {
        return ImmutableList.builder()
                .addAll(ImmutableList.of(myService))
                .addAll(JmsModule.super.makeServices(c))
                .addAll(MailModule.super.makeServices(c))
                .build();
    }

    @Override
    public List<? extends Ctlr> makeControllers(Configuration c) {
        return ImmutableList.of(
                new MyController(myService)
        );
    }

    @Override
    public List<? extends EntityFactory> entityBuilders() {
        return ImmutableList.of(myService);
    }
}

