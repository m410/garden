package org.m410.j8.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;


/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class LoggingDefinitionTest {
    @Test
    public void testFromMap() {
        Map<String,Object> map = new HashMap<>();
        map.put("name","test name");
        LoggingDefinition ad = LoggingDefinition.fromMap(map);
        assertNotNull(ad);
    }
}
