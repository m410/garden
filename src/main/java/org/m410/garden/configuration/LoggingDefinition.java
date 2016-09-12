package org.m410.garden.configuration;

import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;

import java.util.Map;

/**
 * Logging node definition.
 *
 * @author Michael Fortin
 */
public class LoggingDefinition {
    public static LoggingDefinition fromMap(String name, ImmutableHierarchicalConfiguration appDef) {
        LoggingDefinition ld = new LoggingDefinition();
        ModuleNameParser parser = new ModuleNameParser(name);

        return ld;
    }
}
