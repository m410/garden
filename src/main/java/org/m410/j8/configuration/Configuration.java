package org.m410.j8.configuration;


import org.m410.j8.servlet.FilterDefinition;
import org.m410.j8.servlet.ListenerDefinition;
import org.m410.j8.servlet.ServletDefinition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The base file of the configuration.
 * @author Michael Fortin
 */
public class Configuration {
    private String version;
    private ApplicationDefinition application;
    private List<PersistenceDefinition> persistence;
    private List<ModuleDefinition> modules;
    private LoggingDefinition logging;
    private BuildDefinition build;

    private List<FilterDefinition> filterDefinitions;
    private List<ServletDefinition> servletDefinitions;
    private List<ListenerDefinition> listenerDefinitions;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

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

    public LoggingDefinition getLogging() {
        return logging;
    }

    public void setLogging(LoggingDefinition logging) {
        this.logging = logging;
    }

    public BuildDefinition getBuild() {
        return build;
    }

    public void setBuild(BuildDefinition build) {
        this.build = build;
    }

    public List<FilterDefinition> getFilterDefinitions() {
        return filterDefinitions;
    }

    public void setFilterDefinitions(List<FilterDefinition> filterDefinitions) {
        this.filterDefinitions = filterDefinitions;
    }

    public List<ServletDefinition> getServletDefinitions() {
        return servletDefinitions;
    }

    public void setServletDefinitions(List<ServletDefinition> servletDefinitions) {
        this.servletDefinitions = servletDefinitions;
    }

    public List<ListenerDefinition> getListenerDefinitions() {
        return listenerDefinitions;
    }

    public void setListenerDefinitions(List<ListenerDefinition> listenerDefinitions) {
        this.listenerDefinitions = listenerDefinitions;
    }

    public static Configuration fromMap(Map<String, Object> c) {
        Configuration configuration = new Configuration();
        configuration.version = (String)c.getOrDefault("version","UNKNOWN");

        configuration.application = ApplicationDefinition.fromMap(
                (Map<String, Object>)c.getOrDefault("application", new HashMap<String, Object>())
        );

        configuration.build = BuildDefinition.fromMap(
                (Map<String, Object>)c.getOrDefault("build", new HashMap<String, Object>())
        );

        configuration.persistence = ((List<?>)c.getOrDefault("persistence",new ArrayList()))
                .stream().map((val) -> {
                    return PersistenceDefinition.fromMap((Map<String, Object>) val);
                }).collect(Collectors.<PersistenceDefinition>toList());

        configuration.modules = ((List<?>)c.getOrDefault("modules",new ArrayList()))
                .stream().map((val) -> {
                    return ModuleDefinition.fromMap((Map<String, Object>) val);
                }).collect(Collectors.<ModuleDefinition>toList());


        configuration.logging = LoggingDefinition.fromMap(
                (Map<String, Object>)c.getOrDefault("logging", new HashMap<String, Object>())
        );

        return configuration;
    }

    // for later versions
    // dependencies
    // repositories

}
