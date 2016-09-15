package org.m410.garden.configuration;


import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;
import org.m410.garden.servlet.FilterDefinition;
import org.m410.garden.servlet.ListenerDefinition;
import org.m410.garden.servlet.ServletDefinition;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    @SuppressWarnings("unchecked")
    public static Configuration fromMap(ImmutableHierarchicalConfiguration c) {
        Configuration configuration = new Configuration();
        configuration.version = c.getString("version","UNKNOWN");
        configuration.application = ApplicationDefinition.fromMap(c.immutableConfigurationAt("application"));
        configuration.build = BuildDefinition.fromMap(c.immutableConfigurationAt("build"));

        final String[] persistenceModuleName = findName(c, "persistence");
        final String[] moduleNames = findName(c, "modules");
        final String[] loggingModuleName = findName(c, "logging");

        configuration.persistence = Arrays.stream(persistenceModuleName)
                .map(n -> PersistenceDefinition.fromMap(n,c.immutableConfigurationAt(n)))
                .collect(Collectors.toList());

        configuration.modules = Arrays.stream(moduleNames)
                .map(n -> ModuleDefinition.fromMap(n,c.immutableConfigurationAt(n)))
                .collect(Collectors.toList());

        if(loggingModuleName.length>0)
            configuration.logging = LoggingDefinition.fromMap(
                    loggingModuleName[0],
                    c.immutableConfigurationAt(loggingModuleName[0]));

        return configuration;
    }

    private static String[] findName(ImmutableHierarchicalConfiguration c, String prefix) {
        final Iterator<String> keys = c.getKeys();
        final List<String> names = new ArrayList<>();
        final Pattern modulePattern = Pattern.compile("^"+prefix+"\\(.*?\\)$");

        while (keys.hasNext()) {
            String next = keys.next();
            final Matcher matcher = modulePattern.matcher(next);

            if(matcher.matches())
                names.add(next);
        }

        return names.toArray(new String[names.size()]);
    }

    // for later versions
    // dependencies
    // repositories

}
