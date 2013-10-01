package us.m410.j8.configuration;

import java.util.List;

/**
 */
public class Configuration {
    private ApplicationDefinition application;
    private List<PersistenceDefinition> persistence;
    private List<ModuleDefinition> modules;
    private List<LoggingDefinition> logging;

    public ApplicationDefinition getApplication() {
        return application;
    }

    public void setApplication(ApplicationDefinition application) {
        this.application = application;
    }

    public List<PersistenceDefinition> getPersistence() {
        return persistence;
    }

    public void setPersistence(List<PersistenceDefinition> persistence) {
        this.persistence = persistence;
    }

    public List<ModuleDefinition> getModules() {
        return modules;
    }

    public void setModules(List<ModuleDefinition> modules) {
        this.modules = modules;
    }

    public List<LoggingDefinition> getLogging() {
        return logging;
    }

    public void setLogging(List<LoggingDefinition> logging) {
        this.logging = logging;
    }

    // for later versions
    // dependencies
    // repositories

}
