package us.m410.j8.configuration;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class ApplicationDefinition {
    private String name;
    private String version;
    private String packageName;
    private String applicationClass;

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
}
