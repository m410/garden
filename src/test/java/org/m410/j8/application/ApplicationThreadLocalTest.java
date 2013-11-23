package org.m410.j8.application;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

import org.m410.j8.configuration.Configuration;
import org.m410.j8.configuration.ConfigurationFactory;
import org.m410.j8.sample.MyWebApp;

import javax.servlet.ServletException;
import java.io.IOException;
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
        app = new MyWebApp();
        app.init(conf);
    }

    @Test
    public void wrapWithOneThreadLocal() {
        Application.Work work = () -> {
            assertEquals("local", MyThreadLocal.get());
        };

        List<ThreadLocalSessionFactory> factories = new ArrayList<>();
        factories.add(new MyThreadLocalFactory("local"));

        try {
            app.doWithThreadLocal(factories, work);
        }
        catch (IOException|ServletException e) {
            fail("IO exception was thrown");
        }
    }

    @Test
    public void wrapWithManyThreadLocal() {
        Application.Work work = () -> {
            assertEquals("local2", MyThreadLocal.get());
            assertEquals("local3", My2ThreadLocal.get());
        };

        List<ThreadLocalSessionFactory> factories = new ArrayList<>();
        factories.add(new MyThreadLocalFactory("local2"));
        factories.add(new My2ThreadLocalFactory("local3"));

        try {
            app.doWithThreadLocal(factories, work);
        }
        catch (IOException|ServletException e) {
            fail("IO exception was thrown");
        }
    }

}
