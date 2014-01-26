package org.m410.garden.configuration;

import java.util.Map;

/**
 * Logging node definition.
 *
 * @author Michael Fortin
 */
public class LoggingDefinition {
    public static LoggingDefinition fromMap(Map<String, Object> appDef) {
        LoggingDefinition ld = new LoggingDefinition();
        return ld;
    }
}
