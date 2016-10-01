package org.m410.garden.fixtures;


import com.google.common.collect.ImmutableList;
import org.m410.garden.application.Application;
import org.m410.garden.di.ComponentSupplier;
import org.m410.garden.di.Components;
import org.m410.garden.di.ControllerSupplier;
import org.m410.garden.zone.*;

import static org.m410.garden.di.ComponentBuilder.builder;

//import org.m410.garden.module.migration.MigrationModule;
//import org.m410.garden.module.jms.JmsModule;
//import org.m410.garden.module.mail.MailModule;


/**
 */
public class MyWebApp extends Application {

    @Override
    public ComponentSupplier componentProvider() {
        return (zoneManager, configuration) -> Components.init()
                .add(() -> ImmutableList.of(
                        builder(MyServiceDao.class).factory((a, b) -> new MyServiceDaoImpl()),
                        builder(MyService.class).dependsOn(MyServiceDao.class)
                                .factory((a, b) -> new MyServiceImpl((MyServiceDao) b[0]))))
                .withZoneHandler(zoneManager.byName("test").zoneHandlerFactory());
    }

    @Override
    public ControllerSupplier controllerProvider() {
        return (threadLocals, configuration) -> ImmutableList.of(
                new MyController(configuration.typeOf(MyService.class))
        );
    }

    @Override
    public ZoneFactorySupplier zoneFactoryProvider() {
        return (config) -> ImmutableList.of(new MockZoneFactory());
    }

    static class MockZoneFactory implements ZoneFactory {
        private ZoneManager zoneManager;

        @Override
        public void setZoneManager(ZoneManager zoneManager) {
            this.zoneManager = zoneManager;
        }

        @Override
        public String name() {
            return "test";
        }

        @Override
        public Zone makeZone() {
            return new MockZone();
        }

        @Override
        public void shutdown() {

        }

        @Override
        public ZoneHandlerFactory zoneHandlerFactory() {
            return new ZoneHandlerFactory() {
                @Override
                public <T> T proxy(Class<T> interfce, T instance) {
                    return instance;
                }
            };
        }

        class MockZone implements Zone {
            @Override
            public void start() {
                System.out.println("start zone");
            }

            @Override
            public void stop() {
                System.out.println("stop zone");
            }
        }
    }
}

