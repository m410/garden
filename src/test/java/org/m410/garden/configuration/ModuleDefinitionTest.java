package org.m410.garden.configuration;

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
public class ModuleDefinitionTest {
    @Test
    public void testFromMap() {
        Map<String,Object> map = new HashMap<>();
        map.put("name","test name");
        ModuleDefinition ad = ModuleDefinition.fromMap(map);
        assertNotNull(ad);
    }
}
