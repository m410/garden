package org.m410.garden.configuration;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.m410.config.YamlConfiguration;
import org.m410.garden.controller.action.NotAPostException;

import java.io.*;
import java.nio.file.FileSystems;

/**
 */
public class ConfigurationFactory {
    public static final String RUNTIME_CONFIG = "garden.fab.yml";
    public static final String RUNTIME_CONFIG_ALT = "/garden.fab.yml";
    public static final String BUILDTIME_CONFIG = ".fab/config/${env}.yml";

    public static Configuration runtime(String env) {
        InputStream in = ConfigurationFactory.class.getClassLoader().getResourceAsStream(RUNTIME_CONFIG);

        if (in == null)
            in = ConfigurationFactory.class.getClassLoader().getResourceAsStream(RUNTIME_CONFIG_ALT);

        return fromInputStream(in, env);
    }

    public static Configuration buildtime(String env) {
        String filePath = BUILDTIME_CONFIG.replace("${env}", env);
        File file = FileSystems.getDefault().getPath(filePath).toFile();

        try {
            return fromInputStream(new FileInputStream(file), env);
        }
        catch (FileNotFoundException e) {
            throw new NotAPostException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static Configuration fromInputStream(InputStream in, String env) {
        final YamlConfiguration yamlConfiguration = new YamlConfiguration();

        try (InputStreamReader isr = new InputStreamReader(in)){
            yamlConfiguration.read(isr);
            return Configuration.fromMap(yamlConfiguration);
        }
        catch (ConfigurationException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
