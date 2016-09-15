package org.m410.garden.configuration;

import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.m410.config.YamlConfiguration;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;


/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class PersistenceDefinitionTest {
    @Test
    public void testFromMap() throws IOException, ConfigurationException {
        YamlConfiguration configuration = new YamlConfiguration();

        try(InputStreamReader r = new InputStreamReader(getClass().getResourceAsStream("/garden.fab.yml"))) {
            configuration.read(r);
        }

        final String name = "persistence(org..m410..garden..module:garden-jpa:0..1..0)";
        final ImmutableHierarchicalConfiguration val = configuration
                .immutableConfigurationAt(name);

        PersistenceDefinition ad = PersistenceDefinition.fromMap(name, val);
        assertNotNull(ad);
    }
}
