package org.m410.j8.configuration;

import java.util.HashMap;
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

    private Map<String, String> properties;

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


    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public static PersistenceDefinition fromMap(Map<String, Object> val) {
        PersistenceDefinition pd = new PersistenceDefinition();
        pd.setName((String)val.getOrDefault("name","UNKNOWN"));
        pd.setOrganization((String) val.getOrDefault("organization", "UNKNOWN"));
        pd.setVersion((String) val.getOrDefault("version", "UNKNOWN"));
        pd.setProperties((Map<String, String>) val.getOrDefault("properties", new HashMap<String, String>()));
        return pd;
    }
}
