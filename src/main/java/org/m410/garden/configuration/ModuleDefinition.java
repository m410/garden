package org.m410.garden.configuration;

import java.util.Map;

/**
 * Base module definition.
 *
 * @author Michael Fortin
 */
public class ModuleDefinition {
    private String name;
    private String organization;
    private String version;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    public static ModuleDefinition fromMap(Map<String, Object> val) {
        ModuleDefinition md = new ModuleDefinition();
        return md;
    }
}
