package org.m410.garden.fixtures;


import com.google.common.collect.ImmutableList;
import org.m410.garden.application.Application;
import org.m410.garden.di.*;
//import org.m410.garden.module.migration.MigrationModule;
//import org.m410.garden.module.jms.JmsModule;
//import org.m410.garden.module.mail.MailModule;

import static org.m410.garden.di.ComponentBuilder.*;


/**
 */
public class MyWebApp extends Application {
    MyServiceDao myServiceDao = new MyServiceDaoImpl();
    MyService myService = new MyServiceImpl(myServiceDao);
    // todo trxProxy
    // todo jpaTrxProxy(MyService.class, new MyServiceImpl())
    // todo componentService(MailService.class, "mailService")

    @Override
    public ComponentSupplier componentProvider() {
        return (threadLocals, configuration) -> Components.init()
                .add(() -> ImmutableList.of(
                        builder(MyServiceDao.class),
                        builder(MyService.class).dependsOn(MyServiceDao.class)));
    }

    @Override
    public ControllerSupplier controllerProvider() {
        return (threadLocals, configuration) -> ImmutableList.of(
                new MyController(myService)
        );
    }

}

