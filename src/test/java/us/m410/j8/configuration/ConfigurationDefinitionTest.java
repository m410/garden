package us.m410.j8.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * http://maven.apache.org/guides/mini/guide-configuring-plugins.html
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class ConfigurationDefinitionTest {

    @Test
    public void testFromMap() {
        Map<String,Object> map = new HashMap<>();
        map.put("version","0.1.1");
        Configuration ad = Configuration.fromMap(map);
        assertNotNull(ad);
    }
}
