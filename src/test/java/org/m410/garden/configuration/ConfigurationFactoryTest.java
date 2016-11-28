package org.m410.garden.configuration;

import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;
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
    final String configFile = "garden.fab.yml";


    @Test
    public void readSampleConfigurationInDevelopment() {
        InputStream in = getClass().getClassLoader().getResourceAsStream(configFile);
        ImmutableHierarchicalConfiguration configuration = ConfigurationFactory.fromInputStream(in, "development");
        assertNotNull(configuration);
    }

    @Test
    public void readSampleConfigurationInProduction() {
        InputStream in = getClass().getClassLoader().getResourceAsStream(configFile);
        ImmutableHierarchicalConfiguration configuration = ConfigurationFactory.fromInputStream(in, "production");
        assertNotNull(configuration);
    }
}
