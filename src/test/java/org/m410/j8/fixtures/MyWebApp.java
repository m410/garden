package org.m410.j8.fixtures;


import com.google.common.collect.ImmutableList;
import org.m410.j8.application.Application;
import org.m410.j8.configuration.Configuration;
import org.m410.j8.controller.Ctlr;
import org.m410.j8.module.migration.MigrationModule;
import org.m410.j8.module.jpa.JpaModule;
import org.m410.j8.module.ormbuilder.OrmBuilderModule;
import org.m410.j8.module.ormbuilder.orm.EntityFactory;
import org.m410.j8.module.jms.JmsModule;
import org.m410.j8.module.mail.MailModule;

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

