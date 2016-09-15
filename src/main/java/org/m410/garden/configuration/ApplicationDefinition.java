package org.m410.garden.configuration;

import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;

import java.util.Map;

/**
 * Application definition from the configuration file.
 *
 * @author Michael Fortin
 */
public class ApplicationDefinition  {
    private String name;
    private String organization;
    private String version;
    private String packageName;
    private String applicationClass;
    private String author;
    private String description;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getApplicationClass() {
        return applicationClass;
    }

    public void setApplicationClass(String applicationClass) {
        this.applicationClass = applicationClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public static ApplicationDefinition fromMap(ImmutableHierarchicalConfiguration map) {
        ApplicationDefinition ad = new ApplicationDefinition();
        ad.setApplicationClass(map.getString("applicationClass","UNKNOWN"));
        ad.setName(map.getString("name","UNKNOWN"));
        ad.setPackageName(map.getString("packageName","UNKNOWN"));
        ad.setVersion(map.getString("version","UNKNOWN"));
        ad.setAuthor(map.getString("author","UNKNOWN"));
        ad.setDescription(map.getString("description","UNKNOWN"));
        // todo finish me
        return ad;
    }
}
