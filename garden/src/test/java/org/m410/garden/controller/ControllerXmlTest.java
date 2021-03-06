package org.m410.garden.controller;

import com.google.common.collect.ImmutableList;
import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.m410.garden.controller.fixtures.XmlController;
import org.m410.garden.di.ControllerSupplier;
import org.m410.garden.fixtures.MyWebApp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class ControllerXmlTest implements MockServletInput {

    XmlController controller = new XmlController();
    MyWebApp myApp;

    @Before
    public void setup() {
        myApp = new MyWebApp() {
            @Override
            public ControllerSupplier controllerProvider() {
                return (one,two) -> ImmutableList.of(controller);
            }
        };
        myApp.init(null);
    }

    @Test
    public void testGetXml() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/xml");
        when(request.getMethod()).thenReturn("GET");

        HttpServletResponse response = mock(HttpServletResponse.class);
        StringBuffer sb = new StringBuffer();
        when(response.getOutputStream()).thenReturn(servletOutputStream(sb));

        myApp.doRequest(request, response);
        verify(response).setContentType("application/xml");

        verify(request,times(3)).getRequestURI();
        verify(request,times(2)).getMethod();
    }

    @Test
    public void testPostXml() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/xml");
        when(request.getMethod()).thenReturn("POST");
        when(request.getInputStream()).thenReturn(servletInputStream("<f></f>"));

        HttpServletResponse response = mock(HttpServletResponse.class);
        StringBuffer sb = new StringBuffer();
        when(response.getOutputStream()).thenReturn(servletOutputStream(sb));

        assertNotNull(request.getInputStream());
        myApp.doRequest(request, response);
        verify(response).setContentType("application/xml");
        verify(request,times(3)).getRequestURI();
        verify(request,times(3)).getMethod();
    }

    @Test
    public void testPutXml() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/xml/100");
        when(request.getMethod()).thenReturn("PUT");
        when(request.getInputStream()).thenReturn(servletInputStream("<f></f>"));

        HttpServletResponse response = mock(HttpServletResponse.class);
        StringBuffer sb = new StringBuffer();
        when(response.getOutputStream()).thenReturn(servletOutputStream(sb));

        myApp.doRequest(request, response);
        verify(response).setContentType("application/xml");
        verify(request,times(4)).getRequestURI();
        verify(request,times(2)).getMethod();
    }

    @Test
    public void testDeleteXml() throws Exception {

        HttpSession session = mock(HttpSession.class);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/xml/100");
        when(request.getMethod()).thenReturn("DELETE");

        HttpServletResponse response = mock(HttpServletResponse.class);
        StringBuffer sb = new StringBuffer();
        when(response.getOutputStream()).thenReturn(servletOutputStream(sb));

        myApp.doRequest(request, response);
        verify(request,times(5)).getRequestURI();
        verify(request,times(3)).getMethod();
    }
}
