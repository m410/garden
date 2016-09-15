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
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;


/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class LoggingDefinitionTest {
    @Test
    public void testFromMap() throws IOException, ConfigurationException {
        YamlConfiguration configuration = new YamlConfiguration();

        try(InputStreamReader r = new InputStreamReader(getClass().getResourceAsStream("/garden.fab.yml"))) {
            configuration.read(r);
        }

        final String name = "logging(org..m410..garden..logging:logging-logback:0..2..0)";
        final ImmutableHierarchicalConfiguration c = configuration
                .immutableConfigurationAt(name);

        LoggingDefinition ad = LoggingDefinition.fromMap(name, c);
        assertNotNull(ad);
    }
}
