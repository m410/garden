package org.m410.j8.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;


/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class PersistenceDefinitionTest {
    @Test
    public void testFromMap() {
        Map<String,Object> map = new HashMap<>();
        map.put("name","test name");
        PersistenceDefinition ad = PersistenceDefinition.fromMap(map);
        assertNotNull(ad);
    }
}
