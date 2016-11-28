package org.m410.garden.configuration;

import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.m410.config.YamlConfiguration;
import org.m410.garden.controller.action.NotAPostException;

import java.io.*;
import java.nio.file.FileSystems;

/**
 */
public final class ConfigurationFactory {
    private static final String RUNTIME_CONFIG = "garden.fab.yml";
    private static final String RUNTIME_CONFIG_ALT = "/garden.fab.yml";
    private static final String BUILDTIME_CONFIG = ".fab/config/${env}.yml";

    public static ImmutableHierarchicalConfiguration runtime(String env) {
        InputStream in = ConfigurationFactory.class.getClassLoader().getResourceAsStream(RUNTIME_CONFIG);

        if (in == null)
            in = ConfigurationFactory.class.getClassLoader().getResourceAsStream(RUNTIME_CONFIG_ALT);

        return fromInputStream(in, env);
    }

    /**
     * called via reflection in the ReflectConfigFileBuilder class.
     *
     * @param env
     * @return
     */
    public static ImmutableHierarchicalConfiguration buildtime(String env) {
        String filePath = BUILDTIME_CONFIG.replace("${env}", env);
        File file = FileSystems.getDefault().getPath(filePath).toFile();

        try {
            return fromInputStream(new FileInputStream(file), env);
        }
        catch (FileNotFoundException e) {
            throw new NotAPostException(e);
        }
    }

    public static ImmutableHierarchicalConfiguration fromInputStream(InputStream in, String env) {
        final YamlConfiguration yamlConfiguration = new YamlConfiguration();

        try (InputStreamReader isr = new InputStreamReader(in)){
            yamlConfiguration.read(isr);
            return yamlConfiguration;
        }
        catch (ConfigurationException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
