package org.m410.garden.configuration;

import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;

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


    public static ModuleDefinition fromMap(String name, ImmutableHierarchicalConfiguration val) {
        ModuleDefinition md = new ModuleDefinition();
        ModuleNameParser parser = new ModuleNameParser(name);
        md.setName(parser.getName());
        md.setOrganization(parser.getOrg());
        md.setVersion(parser.getVersion());
        return md;
    }
}
