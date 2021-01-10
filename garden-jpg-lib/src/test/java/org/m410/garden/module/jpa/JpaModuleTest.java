package org.m410.garden.module.jpa;

import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.Test;
import org.m410.garden.configuration.ConfigurationFactory;
import org.m410.garden.fixtures.MyWebApp;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Michael Fortin
 */
public class JpaModuleTest {
    @Test
    public void createZone() throws IOException, ConfigurationException {
        InputStream in = getClass().getClassLoader().getResourceAsStream("garden.fab.yml");
        ImmutableHierarchicalConfiguration config = ConfigurationFactory.fromInputStream(in, "development");

        MyWebApp app = new MyWebApp();
        app.init(config);
        assertNotNull(app.makeJpaZone());

        assertNotNull(app.getZoneManager());
        assertEquals(1, app.getZoneManager().getZoneFactories().size());
    }
}
