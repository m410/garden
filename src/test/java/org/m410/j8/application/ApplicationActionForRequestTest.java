package org.m410.j8.application;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.m410.j8.configuration.Configuration;
import org.m410.j8.configuration.ConfigurationFactory;
import org.m410.j8.controller.Ctlr;
import org.m410.j8.fixtures.MyWebApp;

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
    final String configFile = "configuration.m410.yml";
    Application app;

    @Before
    public void setup() {
        InputStream in = getClass().getClassLoader().getResourceAsStream(configFile);
        Configuration conf = ConfigurationFactory.fromInputStream(in, "development");
        app = new MyWebApp() {
            @Override public List<? extends Ctlr> makeControllers(Configuration c) {
                List<Ctlr> ctrls = new ArrayList<>();
                final MockController mockController = new MockController();
                ctrls.add(mockController);
                return ctrls;
            }

            @Override
            public List<? extends ThreadLocalSessionFactory<?>> makeThreadLocalFactories(Configuration c) {
                return null;
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
