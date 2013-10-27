package org.m410.j8.configuration;

import java.util.Map;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class PersistenceDefinition {
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

    public static PersistenceDefinition fromMap(Map<String, Object> val) {
        PersistenceDefinition pd = new PersistenceDefinition();
        return pd;
    }
}
