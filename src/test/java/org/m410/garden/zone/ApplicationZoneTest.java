package org.m410.garden.zone;

import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.m410.garden.application.Application;
import org.m410.garden.configuration.ConfigurationFactory;
import org.m410.garden.fixtures.MyWebApp;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class ApplicationZoneTest {
    final String configFile = "garden.fab.yml";
    Application app;

    @Before
    public void setup() {
        InputStream in = getClass().getClassLoader().getResourceAsStream(configFile);
        ImmutableHierarchicalConfiguration conf = ConfigurationFactory.fromInputStream(in, "development");
        app = new MyWebApp();
        app.init(conf);
    }

    @Test
    public void wrapWithOneThreadLocal() throws Exception {
        Application.Work work = () -> {
            Assert.assertEquals("local", MyZone.get());
            return null;
        };

        List<ZoneFactory> factories = new ArrayList<>();
        factories.add(new MyZoneFactory("local"));
        app.getZoneManager().doWithThreadLocal(factories, work);
    }

    @Test
    public void wrapWithManyThreadLocal() throws Exception {
        Application.Work work = () -> {
            assertEquals("local2", MyZone.get());
            Assert.assertEquals("local3", My2Zone.get());
            return null;
        };

        List<ZoneFactory> factories = new ArrayList<>();
        factories.add(new MyZoneFactory("local2"));
        factories.add(new My2ZoneFactory("local3"));
        app.getZoneManager().doWithThreadLocal(factories, work);
    }
}
