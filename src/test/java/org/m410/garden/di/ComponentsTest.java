package org.m410.garden.di;

import org.junit.Test;
import org.m410.garden.di.app.SampleApplication;
import org.m410.garden.fixtures.MyService;

import static org.junit.Assert.assertNotNull;

/**
 * @author Michael Fortin
 */
public class ComponentsTest {
    @Test
    public void testInitComponents() {
        SampleApplication sampleApplication = new SampleApplication();
        sampleApplication.init(null);
        assertNotNull(sampleApplication.getComponents().typeOf(MyService.class));
    }
}
