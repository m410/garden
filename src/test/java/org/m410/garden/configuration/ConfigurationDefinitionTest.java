package org.m410.garden.configuration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * http://maven.apache.org/guides/mini/guide-configuring-plugins.html
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class ConfigurationDefinitionTest {
    final String configFile = "garden.fab.yml";

    @Before
    public void setup() {

    }

    @Test
    public void testFromMap() {
        Map<String,Object> map = new HashMap<>();
        map.put("version","0.1.1");
        Configuration ad = Configuration.fromMap(map);
        assertNotNull(ad);
    }

    @Test public void loadAllProperties() {
        InputStream in = getClass().getClassLoader().getResourceAsStream(configFile);
        Configuration configuration = ConfigurationFactory.fromInputStream(in, "development");

        assertEquals("0.1.0", configuration.getVersion());
        assertNotNull(configuration.getApplication());
        assertNotNull(configuration.getBuild());
        assertNotNull(configuration.getLogging());
        assertNotNull(configuration.getModules());
        assertEquals(1,configuration.getModules().size());
        assertNotNull(configuration.getPersistence());
        assertEquals(1, configuration.getPersistence().size());

        PersistenceDefinition p = configuration.getPersistence().get(0);
        assertEquals("garden-jpa",p.getName());
        assertNotNull(p.getProperties());
        assertEquals(6,p.getProperties().size());
    }
}
