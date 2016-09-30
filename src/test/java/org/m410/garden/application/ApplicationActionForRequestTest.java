package org.m410.garden.application;

import com.google.common.collect.ImmutableList;
import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.m410.garden.configuration.ConfigurationFactory;
import org.m410.garden.controller.HttpCtlr;
import org.m410.garden.di.ControllerSupplier;
import org.m410.garden.fixtures.MyWebApp;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class ApplicationActionForRequestTest {
    final String configFile = "garden.fab.yml";
    Application app;

    @Before
    public void setup() {
        InputStream in = getClass().getClassLoader().getResourceAsStream(configFile);
        ImmutableHierarchicalConfiguration conf = ConfigurationFactory.fromInputStream(in, "development");
        app = new MyWebApp() {
            @Override public ControllerSupplier controllerProvider() {
                return (config,components) -> ImmutableList.of(
                        new MockController()
                );
            }
        };
        app.init(conf);
    }

    @Test
    public void actionForRequestNotFound() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/mock");
        when(request.getMethod()).thenReturn("GET");

        assertTrue(app.actionForRequest(request).isPresent());
    }

    @Test
    public void actionForRequestFound() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/people");
        when(request.getMethod()).thenReturn("POST");

        assertFalse(app.actionForRequest(request).isPresent());
    }

}
