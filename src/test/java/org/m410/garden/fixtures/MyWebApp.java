package org.m410.garden.fixtures;


import com.google.common.collect.ImmutableList;
import org.m410.garden.application.Application;
import org.m410.garden.configuration.Configuration;
import org.m410.garden.controller.HttpCtrl;
import org.m410.garden.module.migration.MigrationModule;
import org.m410.garden.module.jms.JmsModule;
import org.m410.garden.module.mail.MailModule;

import java.util.List;


/**
 */
public class MyWebApp extends Application implements MailModule, JmsModule,  MigrationModule {
    MyServiceDao myServiceDao = new MyServiceDaoImpl();
    MyService myService = new MyServiceImpl(myServiceDao);
    // todo trxProxy
    // todo jpaTrxProxy(MyService.class, new MyServiceImpl())
    // todo componentService(MailService.class, "mailService")

    @Override public List<?> makeServices(Configuration c) {
        return ImmutableList.builder()
                .addAll(ImmutableList.of(myService))
                .addAll(JmsModule.super.makeServices(c))
                .addAll(MailModule.super.makeServices(c))
                .build();
    }

    @Override public List<? extends HttpCtrl> makeControllers(Configuration c) {
        return ImmutableList.of(
                new MyController(myService)
        );
    }

}

