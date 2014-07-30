package org.m410.garden.application;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

import org.m410.garden.configuration.Configuration;
import org.m410.garden.configuration.ConfigurationFactory;
import org.m410.garden.fixtures.MyWebApp;
import org.m410.garden.transaction.ThreadLocalSessionFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class ApplicationThreadLocalTest {
    final String configFile = "configuration.m410.yml";
    Application app;

    @Before
    public void setup() {
        InputStream in = getClass().getClassLoader().getResourceAsStream(configFile);
        Configuration conf = ConfigurationFactory.fromInputStream(in, "development");
        app = new MyWebApp(){

            @Override
            public List<? extends ThreadLocalSessionFactory<?>> makeThreadLocalFactories(Configuration c) {
                return null;
            }
        };
        app.init(conf);
    }

    @Test
    public void wrapWithOneThreadLocal() throws Exception {
        Application.Work work = () -> {
            assertEquals("local", MyThreadLocal.get());
            return null;
        };

        List<ThreadLocalSessionFactory> factories = new ArrayList<>();
        factories.add(new MyThreadLocalFactory("local"));
        app.doWithThreadLocal(factories, work);
    }

    @Test
    public void wrapWithManyThreadLocal() throws Exception {
        Application.Work work = () -> {
            assertEquals("local2", MyThreadLocal.get());
            assertEquals("local3", My2ThreadLocal.get());
            return null;
        };

        List<ThreadLocalSessionFactory> factories = new ArrayList<>();
        factories.add(new MyThreadLocalFactory("local2"));
        factories.add(new My2ThreadLocalFactory("local3"));
        app.doWithThreadLocal(factories, work);
    }

}
