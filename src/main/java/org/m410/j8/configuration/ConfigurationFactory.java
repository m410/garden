package org.m410.j8.configuration;

import org.m410.j8.action.NotAPostException;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.FileSystems;
import java.util.Map;

/**
 */
public class ConfigurationFactory {
    public static final String  configFile = "configuration.m410.yml";
    public static final String  altConfigFile = "/configuration.m410.yml";

    public static Configuration runtime(String env) {
        InputStream in = ConfigurationFactory.class.getClassLoader().getResourceAsStream(configFile);

        if (in == null)
            in = ConfigurationFactory.class.getClassLoader().getResourceAsStream(altConfigFile);

        return fromInputStream(in, env);
    }

    public static Configuration buildtime(String env) {
        File file = FileSystems.getDefault().getPath(configFile).toFile();
        try {
            return fromInputStream(new FileInputStream(file), env);
        }
        catch (FileNotFoundException e) {
            throw new NotAPostException(e);
        }
    }

    public static Configuration fromInputStream(InputStream in, String env) {
        final Map<String, Object> map = (Map<String, Object>) new Yaml().load(in);
        return Configuration.fromMap(map);
    }
}
