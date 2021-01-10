package org.m410.garden.application;

import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.m410.garden.configuration.ConfigurationFactory;
import org.m410.garden.fixtures.MyWebApp;

import java.io.InputStream;

import static org.junit.Assert.assertNotNull;

/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class GardenApplicationTest {
    final String configFile = "garden.fab.yml";

    @Test
    public void applicationLoad() {
        InputStream in = getClass().getClassLoader().getResourceAsStream(configFile);
        ImmutableHierarchicalConfiguration conf = ConfigurationFactory.fromInputStream(in,"development");
        GardenApplication app = new MyWebApp();
        app.init(conf);
        assertNotNull(app);
    }

    @Test
    public void applicationStartup() {
        InputStream in = getClass().getClassLoader().getResourceAsStream(configFile);
        ImmutableHierarchicalConfiguration conf = ConfigurationFactory.fromInputStream(in,"development");
        GardenApplication app = new MyWebApp();
        app.init(conf);
        assertNotNull(app);
        assertNotNull(app.getActionDefinitions().size() == 0);
    }

    @Test
    public void applicationShutdown() {
        InputStream in = getClass().getClassLoader().getResourceAsStream(configFile);
        ImmutableHierarchicalConfiguration conf = ConfigurationFactory.fromInputStream(in,"development");
        GardenApplication app = new MyWebApp();
        app.init(conf);
        app.destroy();
        assertNotNull(app);
    }
}
