package us.m410.j8.configuration;

import org.yaml.snakeyaml.Yaml;
import us.m410.j8.action.RuntimeIOException;

import java.io.*;
import java.nio.file.FileSystems;
import java.util.Map;

/**
 */
public class ConfigurationFactory {
    public static final String  configFile = "configuration.m410.yml";

    public static Configuration runtime(String env) {
        InputStream in = ConfigurationFactory.class.getClassLoader().getResourceAsStream(configFile);
        return fromInputStream(in, env);
    }

    public static Configuration buildtime(String env) {
        File file = FileSystems.getDefault().getPath(configFile).toFile();
        try {
            return fromInputStream(new FileInputStream(file), env);
        }
        catch (FileNotFoundException e) {
            throw new RuntimeIOException(e);
        }
    }

    public static Configuration fromInputStream(InputStream in, String env) {
        try (InputStream stream = in) {
            return Configuration.fromMap((Map<String, Object>)new Yaml().load(stream));
        }
        catch (IOException e) {
            throw new RuntimeIOException(e);
        }
    }
}
