package org.m410.j8.controller;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.m410.j8.application.ThreadLocalSessionFactory;
import org.m410.j8.configuration.Configuration;
import org.m410.j8.sample.MyWebApp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class ControllerJsonTest implements MockServletInput{

    JsonController controller = new JsonController();
    MyWebApp myApp;

    @Before
    public void setup() {
        myApp = new MyWebApp() {
            @Override public List<Ctlr> makeControllers(Configuration c) {
                return ImmutableList.of(controller);
            }

            @Override
            public List<ThreadLocalSessionFactory> makeThreadLocalFactories(Configuration c) {
                return null;
            }
        };
        myApp.init(null);
    }


    @Test
    public void testGetXml() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/json");
        when(request.getMethod()).thenReturn("GET");

        HttpServletResponse response = mock(HttpServletResponse.class);
        StringBuffer sb = new StringBuffer();
        when(response.getOutputStream()).thenReturn(servletOutputStream(sb));

        myApp.doRequest(request, response);
        verify(response).setContentType("application/json");
    }

    @Test
    public void testPostXml() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/json");
        when(request.getMethod()).thenReturn("POST");
        when(request.getInputStream()).thenReturn(servletInputStream("{\"f\":\"d\"}"));

        HttpServletResponse response = mock(HttpServletResponse.class);
        StringBuffer sb = new StringBuffer();
        when(response.getOutputStream()).thenReturn(servletOutputStream(sb));

        myApp.doRequest(request, response);
        verify(response).setContentType("application/json");
    }

    @Test
    public void testPutXml() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/json/100");
        when(request.getMethod()).thenReturn("PUT");
        when(request.getInputStream()).thenReturn(servletInputStream("{\"f\":\"d\"}"));

        HttpServletResponse response = mock(HttpServletResponse.class);
        StringBuffer sb = new StringBuffer();
        when(response.getOutputStream()).thenReturn(servletOutputStream(sb));

        myApp.doRequest(request, response);
        verify(response).setContentType("application/json");
    }

    @Test
    public void testDeleteXml() throws IOException {
        HttpSession session = mock(HttpSession.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/json/100");
        when(request.getMethod()).thenReturn("DELETE");

        HttpServletResponse response = mock(HttpServletResponse.class);
        StringBuffer sb = new StringBuffer();
        when(response.getOutputStream()).thenReturn(servletOutputStream(sb));

        myApp.doRequest(request, response);
    }
}
