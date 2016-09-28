package org.m410.garden.controller;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.m410.garden.configuration.Configuration;
import org.m410.garden.controller.fixtures.JsonController;
import org.m410.garden.fixtures.MyWebApp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

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
            @Override public List<? extends HttpCtlr> makeControllers(Configuration c) {
                return ImmutableList.of(controller);
            }
        };
        myApp.init(null);
    }


    @Test
    public void testGetJson() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/json");
        when(request.getMethod()).thenReturn("GET");

        HttpServletResponse response = mock(HttpServletResponse.class);
        StringBuffer sb = new StringBuffer();
        when(response.getOutputStream()).thenReturn(servletOutputStream(sb));

        myApp.doRequest(request, response);

        verify(request,times(2)).getContextPath();
        verify(request,times(3)).getRequestURI();
        verify(request,times(2)).getMethod();

        verify(response).getOutputStream();
        verify(response).setContentType("application/json");
    }

    @Test
    public void testPostJson() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/json");
        when(request.getMethod()).thenReturn("POST");
        when(request.getInputStream()).thenReturn(servletInputStream("{\"f\":\"d\"}"));

        HttpServletResponse response = mock(HttpServletResponse.class);
        StringBuffer sb = new StringBuffer();
        when(response.getOutputStream()).thenReturn(servletOutputStream(sb));

        myApp.doRequest(request, response);

        verify(request,times(2)).getContextPath();
        verify(request,times(3)).getRequestURI();
        verify(request,times(3)).getMethod();

        verify(response).getOutputStream();
        verify(response).setContentType("application/json");
    }

    @Test
    public void testPutJson() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/json/100");
        when(request.getMethod()).thenReturn("PUT");
        when(request.getInputStream()).thenReturn(servletInputStream("{\"f\":\"d\"}"));

        HttpServletResponse response = mock(HttpServletResponse.class);
        StringBuffer sb = new StringBuffer();
        when(response.getOutputStream()).thenReturn(servletOutputStream(sb));

        myApp.doRequest(request, response);

        verify(request,times(3)).getContextPath();
        verify(request,times(4)).getRequestURI();
        verify(request,times(2)).getMethod();

        verify(response).getOutputStream();
        verify(response).setContentType("application/json");
    }

    @Test
    public void testDeleteJson() throws Exception {
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

        verify(request,times(4)).getContextPath();
        verify(request,times(5)).getRequestURI();
        verify(request,times(3)).getMethod();

//        verify(response).getOutputStream();
//        verify(response).setContentType("application/json");
    }
}
