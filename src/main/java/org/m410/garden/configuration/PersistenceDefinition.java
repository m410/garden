package org.m410.garden.configuration;

import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A persistence module definition.
 *
 * @author Michael Fortin
 */
public class PersistenceDefinition {
    private String name;
    private String organization;
    private String version;
    private List<String> classes;

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

    public List<String> getClasses() {
        return classes;
    }

    public void setClasses(List<String> classes) {
        this.classes = classes;
    }

    @SuppressWarnings("unchecked")
    public static PersistenceDefinition fromMap(String name, ImmutableHierarchicalConfiguration val) {
        PersistenceDefinition pd = new PersistenceDefinition();
        ModuleNameParser parser = new ModuleNameParser(name);
        pd.setName(parser.getName());
        pd.setOrganization(parser.getOrg());
        pd.setVersion(parser.getVersion());

        pd.setProperties(val.get(Map.class, "properties", new HashMap<String, Object>()));
        pd.setClasses(val.getList(String.class, "classes", new ArrayList<>()));

        return pd;
    }

    @Override
    public String toString() {
        return "PersistenceDefinition{" +
               "name='" + name + '\'' +
               ", organization='" + organization + '\'' +
               ", version='" + version + '\'' +
               '}';
    }
}
