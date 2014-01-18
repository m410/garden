package org.m410.j8.transactional;

import org.m410.j8.application.Application;
import org.m410.j8.configuration.Configuration;
import org.m410.j8.controller.Ctlr;
import org.m410.j8.module.jpa.JpaModule;
import org.m410.j8.transaction.ThreadLocalSessionFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @author m410
 */
public class TrxApplication extends Application {

    MyService myService = transactional(MyService.class, new MyServiceImpl());

    @Override
    public List<? extends ThreadLocalSessionFactory> makeThreadLocalFactories(Configuration c) {
        return Arrays.asList(new TrxThreadLocalSessionFactory());
    }

    @Override
    public List<? extends Ctlr> makeControllers(Configuration c) {
        return Arrays.asList();
    }
}
