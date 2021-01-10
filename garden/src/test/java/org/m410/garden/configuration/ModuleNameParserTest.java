package org.m410.garden.configuration;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Michael Fortin
 */
public class ModuleNameParserTest {

    @Test
    public void createModuleNameParser() {
        String name = "logging(org..m410..garden..logging:logging-logback:0..2..0) ";
        ModuleNameParser parser = new ModuleNameParser(name);
        assertNotNull(parser);
        assertEquals("logging-logback",parser.getName());
        assertEquals("org.m410.garden.logging",parser.getOrg());
        assertEquals("0.2.0",parser.getVersion());
    }
}