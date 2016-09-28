package org.m410.garden.fixtures;


import com.google.common.collect.ImmutableList;
import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;
import org.m410.garden.application.Application;
import org.m410.garden.controller.HttpCtlr;
//import org.m410.garden.module.migration.MigrationModule;
//import org.m410.garden.module.jms.JmsModule;
//import org.m410.garden.module.mail.MailModule;

import java.util.List;


/**
 */
public class MyWebApp extends Application {
    MyServiceDao myServiceDao = new MyServiceDaoImpl();
    MyService myService = new MyServiceImpl(myServiceDao);
    // todo trxProxy
    // todo jpaTrxProxy(MyService.class, new MyServiceImpl())
    // todo componentService(MailService.class, "mailService")

    @Override public List<?> makeServices(ImmutableHierarchicalConfiguration c) {
        return ImmutableList.of(ImmutableList.of(myService));
    }

    @Override public List<? extends HttpCtlr> makeControllers(ImmutableHierarchicalConfiguration c) {
        return ImmutableList.of(
                new MyController(myService)
        );
    }
}

