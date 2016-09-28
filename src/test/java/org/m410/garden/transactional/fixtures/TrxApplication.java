package org.m410.garden.transactional.fixtures;

import com.google.common.collect.ImmutableList;
import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;
import org.m410.garden.application.Application;
import org.m410.garden.controller.HttpCtlr;
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
    public List<? extends ThreadLocalSessionFactory> makeThreadLocalFactories(ImmutableHierarchicalConfiguration c) {
        return ImmutableList.of(new TrxThreadLocalSessionFactory());
    }

    @Override
    public List<? extends HttpCtlr> makeControllers(ImmutableHierarchicalConfiguration c) {
        return ImmutableList.of(new TrxController(myService));
    }
}
