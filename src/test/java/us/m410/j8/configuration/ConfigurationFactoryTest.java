package us.m410.j8.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.InputStream;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * http://maven.apache.org/guides/mini/guide-configuring-plugins.html
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class ConfigurationFactoryTest {
    final String configFile = "configuration.m410.yml";


    @Test
    public void readSampleConfiguration() {
        InputStream in = getClass().getClassLoader().getResourceAsStream(configFile);
        Configuration configuration = ConfigurationFactory.fromInputStream(in, "development");
        assertNotNull(configuration);
    }

}
