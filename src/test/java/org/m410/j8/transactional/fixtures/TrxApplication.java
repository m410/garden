package org.m410.j8.transactional.fixtures;

import com.google.common.collect.ImmutableList;
import org.m410.j8.application.Application;
import org.m410.j8.configuration.Configuration;
import org.m410.j8.controller.Ctlr;
import org.m410.j8.transaction.ThreadLocalSessionFactory;

import java.util.List;

/**
 * @author m410
 */
public class TrxApplication extends Application {

    MyService myService = transactional(MyService.class, new MyServiceImpl());

    public MyService getMyService() {
        return myService;
    }

    @Override
    public List<? extends ThreadLocalSessionFactory> makeThreadLocalFactories(Configuration c) {
        return ImmutableList.of(new TrxThreadLocalSessionFactory());
    }

    @Override
    public List<? extends Ctlr> makeControllers(Configuration c) {
        return ImmutableList.of(new TrxController(myService));
    }
}
