package us.m410.j8.application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import us.m410.j8.configuration.Configuration;
import us.m410.j8.configuration.ConfigurationFactory;
import us.m410.j8.sample.MyWebApp;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class ApplicationTest {

    @Test
    public void applicationLoad() {
        Configuration conf = ConfigurationFactory.buildtime("development");
        Application app = new MyWebApp(conf);
        assertNotNull(app);
    }

    @Test
    public void applicationStartup() {
        assertTrue("Implement me", false);
    }

    @Test
    public void applicationShutdown() {
        assertTrue("Implement me", false);
    }
}
