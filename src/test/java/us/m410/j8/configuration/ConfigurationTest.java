package us.m410.j8.configuration;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import us.m410.j8.application.Application;
import us.m410.j8.sample.MyWebApp;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;

/**
 * http://maven.apache.org/guides/mini/guide-configuring-plugins.html
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class ConfigurationTest {

    @Test
    public void writeSampleConfiguration() {
        XStream xstream = new XStream(new StaxDriver());
        xstream.alias("configuration", Configuration.class);
        xstream.alias("application", ApplicationDefinition.class);
        ApplicationDefinition applicationDefinition = new ApplicationDefinition();
        applicationDefinition.setName("my name");
        applicationDefinition.setApplicationClass("org.m410.demo.DemoApplication");
        Configuration configuration = new Configuration();
        configuration.setApplication(applicationDefinition);
        String xml = xstream.toXML(configuration);
        assertNotNull(xml);
        System.out.println(xml);
    }

    @Test
    public void readSampleConfiguration() {
        String sample = "<configuration>" +
                "<application>" +
                "<name>my name</name>" +
                "<applicationClass>org.m410.demo.DemoApplication</applicationClass>" +
                "</application>" +
                "</configuration>";

        XStream xstream = new XStream(new StaxDriver());
        xstream.alias("configuration", Configuration.class);
        xstream.alias("application", ApplicationDefinition.class);
        Configuration configuration = (Configuration)xstream.fromXML(sample);
        assertNotNull(configuration);
        assertEquals("my name",configuration.getApplication().getName());

    }
    @Test
    public void applicationLoadInWebapp() {
        Configuration conf = ConfigurationBuilder.buildtime("development");
        Application app = new MyWebApp(conf);
        assertNotNull(app);
    }

    @Test
    public void applicationLoadInDevMode() {
        assertTrue("Implement me", false);
    }
}
