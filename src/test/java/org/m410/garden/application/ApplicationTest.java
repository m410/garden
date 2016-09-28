package org.m410.garden.application;

import com.google.common.collect.ImmutableList;
import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.m410.garden.configuration.ConfigurationFactory;
import org.m410.garden.fixtures.MyWebApp;
import org.m410.garden.transaction.ThreadLocalSession;
import org.m410.garden.transaction.ThreadLocalSessionFactory;

import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class ApplicationTest {
    final String configFile = "garden.fab.yml";

    @Test
    public void applicationLoad() {
        InputStream in = getClass().getClassLoader().getResourceAsStream(configFile);
        ImmutableHierarchicalConfiguration conf = ConfigurationFactory.fromInputStream(in,"development");
        Application app = new MyWebApp();
        app.init(conf);
        assertNotNull(app);
    }

    @Test
    public void applicationStartup() {
        InputStream in = getClass().getClassLoader().getResourceAsStream(configFile);
        ImmutableHierarchicalConfiguration conf = ConfigurationFactory.fromInputStream(in,"development");
        Application app = new MyWebApp(){

            @Override
            public List<? extends ThreadLocalSessionFactory<?>> makeThreadLocalFactories(ImmutableHierarchicalConfiguration c) {
                return ImmutableList.of();
            }
        };
        app.init(conf);
        assertNotNull(app);
        assertNotNull(app.getActionDefinitions().size() == 0);
    }

    @Test
    public void applicationShutdown() {
        InputStream in = getClass().getClassLoader().getResourceAsStream(configFile);
        ImmutableHierarchicalConfiguration conf = ConfigurationFactory.fromInputStream(in,"development");
        Application app = new MyWebApp(){


            @Override
            public List<? extends ThreadLocalSessionFactory<?>> makeThreadLocalFactories(ImmutableHierarchicalConfiguration c) {
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
