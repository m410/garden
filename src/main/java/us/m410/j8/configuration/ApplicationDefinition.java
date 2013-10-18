package us.m410.j8.configuration;

import java.util.Map;

/**
 * Document Me..
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

    public static ApplicationDefinition fromMap(Map<String, Object> map) {
        System.out.println("applicationDefinition map:"+map);
        ApplicationDefinition ad = new ApplicationDefinition();
        ad.setApplicationClass((String)map.getOrDefault("applicationClass","UNKNOWN"));
        ad.setName((String) map.getOrDefault("name","UNKNOWN"));
        ad.setPackageName((String) map.getOrDefault("packageName","UNKNOWN"));
        ad.setVersion((String) map.getOrDefault("version","UNKNOWN"));
        ad.setAuthor((String) map.getOrDefault("author","UNKNOWN"));
        ad.setDescription((String) map.getOrDefault("description","UNKNOWN"));
        // todo finish me
        return ad;
    }
}
