package us.m410.j8.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import us.m410.j8.application.Application;
import us.m410.j8.sample.MyWebApp;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class ConfigurationTest {

    @Test
    public void applicationLoadInWebapp() {
        Configuration conf = new ConfigurationBuilder(null, "").configure();
        Application app = new MyWebApp(conf);
        assertNotNull(app);
    }

    @Test
    public void applicationLoadInDevMode() {
        assertTrue("Implement me", false);
    }
}
