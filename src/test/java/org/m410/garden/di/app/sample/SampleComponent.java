package org.m410.garden.di.app.sample;

import com.google.common.collect.ImmutableList;
import org.m410.garden.di.Component;
import org.m410.garden.di.ComponentBuilder;
import org.m410.garden.fixtures.MyService;
import org.m410.garden.fixtures.MyServiceDao;
import org.m410.garden.fixtures.MyServiceDaoImpl;
import org.m410.garden.fixtures.MyServiceImpl;

import java.util.List;

import static org.m410.garden.di.ComponentBuilder.builder;

/**
 * @author Michael Fortin
 */
public class SampleComponent implements Component {

    public List<ComponentBuilder> builders() {
        return ImmutableList.of(service, dao);
    }

    //  These services should really be a child of this package.
    ComponentBuilder<MyService> service = builder(MyService.class)
            .dependsOn(MyServiceDao.class)
            .factory((transaction, dependencies) -> {
                return transaction.proxy(MyService.class, new MyServiceImpl((MyServiceDao)dependencies[0]));
            });

    ComponentBuilder<MyServiceDao> dao = builder(MyServiceDao.class)
            .factory((transaction, dependencies) -> new MyServiceDaoImpl());

}
