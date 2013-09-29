package us.m410.j8.configuration;

/**
 */
public class ConfigurationBuilder {


    public ConfigurationBuilder(java.io.File basePath, String env) {
    }

    public Configuration configure() {
        return new Configuration() {
            @Override
            public ApplicationDescription application() {
                return null;
            }
        };
    }

    public static Configuration runtime(String env) {
        return null;
    }

}
