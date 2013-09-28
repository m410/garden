package us.m410.j8.sample;


import com.google.common.collect.ImmutableList;
import us.m410.j8.application.Application;
import us.m410.j8.configuration.Configuration;
import us.m410.j8.controller.Controller;
import us.m410.j8.orm.JpaComponent;
import us.m410.j8.orm.MigrationComponent;
import us.m410.j8.orm.OrmBuilderComponent;
import us.m410.j8.orm.OrmGenerator;
import us.m410.j8.sample.components.JmsComponent;
import us.m410.j8.sample.components.MailComponent;

import java.util.List;


/**
 */
public class MyWebApp
        extends Application
        implements JpaComponent, MailComponent, JmsComponent,
        OrmBuilderComponent, MigrationComponent {

    public MyWebApp(Configuration c) {
        super(c);
    }

    MyServiceDao myServiceDao = new MyServiceDaoImpl();
    MyService myService = new MyServiceImpl(myServiceDao);

    @Override
    public List<?> makeServices(Configuration c) {
        return ImmutableList.builder()
                .addAll(ImmutableList.of(myService))
                .addAll(JmsComponent.super.makeServices(c))
                .addAll(MailComponent.super.makeServices(c))
                .build();
    }

    @Override
    public List<? extends Controller> makeControllers(Configuration c) {
        return ImmutableList.of(
                new MyController(myService)
        );
    }

    @Override
    public List<? extends OrmGenerator> ormGenerators() {
        return ImmutableList.of(
                myServiceDao
        );
    }


}

