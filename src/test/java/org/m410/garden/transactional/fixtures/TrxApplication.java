package org.m410.garden.transactional.fixtures;

import com.google.common.collect.ImmutableList;
import org.m410.garden.application.Application;
import org.m410.garden.configuration.Configuration;
import org.m410.garden.controller.HttpCtrl;
import org.m410.garden.transaction.ThreadLocalSessionFactory;

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
    public List<? extends HttpCtrl> makeControllers(Configuration c) {
        return ImmutableList.of(new TrxController(myService));
    }
}
