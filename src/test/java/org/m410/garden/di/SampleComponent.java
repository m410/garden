package org.m410.garden.di;

import com.google.common.collect.ImmutableList;
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

    ComponentBuilder<MyService> service = builder(MyService.class)
            .dependsOn(MyServiceDao.class)
            .constructor((transaction, dependencies) -> {
                return transaction.required(MyService.class, new MyServiceImpl(dependencies.typeOf(MyServiceDao.class)));
            });

    ComponentBuilder<MyServiceDao> dao = builder(MyServiceDao.class)
            .constructor((transaction, dependencies) -> new MyServiceDaoImpl());

}
