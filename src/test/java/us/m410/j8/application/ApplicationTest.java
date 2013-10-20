package us.m410.j8.application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import us.m410.j8.configuration.Configuration;
import us.m410.j8.configuration.ConfigurationFactory;
import us.m410.j8.sample.MyWebApp;

import java.io.InputStream;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class ApplicationTest {
    final String configFile = "configuration.m410.yml";

    @Test
    public void applicationLoad() {
        InputStream in = getClass().getClassLoader().getResourceAsStream(configFile);
        Configuration conf = ConfigurationFactory.fromInputStream(in,"development");
        Application app = new MyWebApp();
        app.init(conf);
        assertNotNull(app);
    }

    @Test
    public void applicationStartup() {
        InputStream in = getClass().getClassLoader().getResourceAsStream(configFile);
        Configuration conf = ConfigurationFactory.fromInputStream(in,"development");
        Application app = new MyWebApp();
        app.init(conf);
        assertNotNull(app);
        assertNotNull(app.actionDefinitions.size() == 0);
    }

    @Test
    public void applicationShutdown() {
        InputStream in = getClass().getClassLoader().getResourceAsStream(configFile);
        Configuration conf = ConfigurationFactory.fromInputStream(in,"development");
        Application app = new MyWebApp();
        app.init(conf);
        app.destroy();
        assertNotNull(app);
    }
}
