package org.m410.j8.application;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.m410.j8.configuration.Configuration;
import org.m410.j8.configuration.ConfigurationFactory;
import org.m410.j8.sample.MyWebApp;

import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class ApplicationTest {
    final String configFile = "configuration.m410.yml";

    @Test
    public void applicationLoad() {
        InputStream in = getClass().getClassLoader().getResourceAsStream(configFile);
        Configuration conf = ConfigurationFactory.fromInputStream(in,"development");
        Application app = new MyWebApp() {
            @Override
            public List<ThreadLocalSessionFactory> makeThreadLocalFactories(Configuration c) {
                return null;
            }
        };
        app.init(conf);
        assertNotNull(app);
    }

    @Test
    public void applicationStartup() {
        InputStream in = getClass().getClassLoader().getResourceAsStream(configFile);
        Configuration conf = ConfigurationFactory.fromInputStream(in,"development");
        Application app = new MyWebApp(){

            @Override
            public List<ThreadLocalSessionFactory> makeThreadLocalFactories(Configuration c) {
                return null;
            }
        };
        app.init(conf);
        assertNotNull(app);
        assertNotNull(app.getActionDefinitions().size() == 0);
    }

    @Test
    public void applicationShutdown() {
        InputStream in = getClass().getClassLoader().getResourceAsStream(configFile);
        Configuration conf = ConfigurationFactory.fromInputStream(in,"development");
        Application app = new MyWebApp(){


            @Override
            public List<ThreadLocalSessionFactory> makeThreadLocalFactories(Configuration c) {
                return ImmutableList.of(
                        new ThreadLocalSessionFactory<ThreadLocalSession<String>>() {
                            @Override
                            public ThreadLocalSession<String> make() {
                                return null;
                            }

                            @Override
                            public void shutdown() {
//                                System.out.println("shutdown called");
                            }
                        }
                );
            }
        };
        app.init(conf);
        app.destroy();
        assertNotNull(app);
    }
}
