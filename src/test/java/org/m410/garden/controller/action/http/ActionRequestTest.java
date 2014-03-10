package org.m410.garden.controller.action.http;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.m410.garden.controller.action.PathExpr;
import org.m410.garden.controller.MockServletInput;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class ActionRequestTest implements MockServletInput {

    @Test
    public void isActiveSessionNot() {
        HttpServletRequest request = mock(HttpServletRequest.class) ;
        PathExpr path = new PathExpr("/persons/12/children");
        ActionRequest ar = new ActionRequestDefaultImpl(request, path);
        assertNotNull(ar);
        assertFalse(ar.isActiveSession());
    }

    @Test
    public void isActiveSessionYes() {
        HttpSession mockSession = mock(HttpSession.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getSession(false)).thenReturn(mockSession);


        PathExpr path = new PathExpr("/persons/12/children");
        ActionRequest ar = new ActionRequestDefaultImpl(request, path);
        assertNotNull(ar);
        assertTrue(ar.isActiveSession());

        verify(request,times(3)).getSession(false);
    }

    @Test
    public void getSession() {
        HttpSession session = mock(HttpSession.class);
        Hashtable<String,String> map = new Hashtable<>();
        map.put("name","value");
        when(session.getAttributeNames()).thenReturn(map.keys());
        when(session.getAttribute("name")).thenReturn("value");

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getSession(false)).thenReturn(session);

        PathExpr path = new PathExpr("/persons/12/children");
        ActionRequest ar = new ActionRequestDefaultImpl(request, path);
        assertNotNull(ar);
        assertEquals(1, ar.session().size());


        verify(session).getAttributeNames();
        verify(session).getAttribute("name");
        verify(request,times(5)).getSession(false);
    }


    @Test
    public void requestHeaders() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Hashtable<String,String> map = new Hashtable<>();
        map.put("name","value");
        when(request.getHeaderNames()).thenReturn(map.keys());
        when(request.getHeader("name")).thenReturn("value");

        PathExpr path = new PathExpr("/persons/12/children");
        ActionRequest ar = new ActionRequestDefaultImpl(request, path);
        assertNotNull(ar);
        assertEquals(1, ar.headers().size());


        verify(request).getHeaderNames();
        verify(request).getHeader("name");
    }

    @Test
    public void urlParameters() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/persons/21/address");
//        when(request.getMethod()).thenReturn("GET");

        PathExpr path = new PathExpr("/persons/{id}/children");
        ActionRequest ar = new ActionRequestDefaultImpl(request, path);
        assertNotNull(ar);
        assertEquals(1, ar.url().size());


        verify(request).getContextPath();
        verify(request).getRequestURI();
//        verify(request).getMethod();
    }

    @Test
    public void requestParameters() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Map<String,String[]> map = new HashMap<>();
        map.put("one",new String[]{"two"});
        when(request.getParameterMap()).thenReturn(map);

        PathExpr path = new PathExpr("/persons/12/children");
        ActionRequest ar = new ActionRequestDefaultImpl(request, path);
        assertNotNull(ar);
        assertEquals(1, ar.request().size());

        verify(request).getParameterMap();
    }

    @Test
    public void requestParams() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Map<String,String[]> map = new HashMap<>();
        map.put("one",new String[]{"two"});
        when(request.getParameterMap()).thenReturn(map);

        PathExpr path = new PathExpr("/persons/12/children");
        ActionRequest ar = new ActionRequestDefaultImpl(request, path);
        assertNotNull(ar);
        assertEquals(1, ar.params().size());
        assertEquals("two", ar.params().get("one"));

        verify(request,times(2)).getParameterMap();
    }

    @Test
    public void  userPrincipal() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        PathExpr path = new PathExpr("/persons/12/children");
        ActionRequest ar = new ActionRequestDefaultImpl(request, path);
        assertNotNull(ar);
        assertNotNull(ar.identity());
    }

    @Test
    public void postBodyAsStream() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getInputStream()).thenReturn(servletInputStream("hello"));

        PathExpr path = new PathExpr("/persons/12/children");
        ActionRequest ar = new ActionRequestDefaultImpl(request, path);
        assertNotNull(ar);
        assertNotNull(ar.bodyAsStream());

        verify(request).getInputStream();
    }

    @Test
    public void postBodyAsString() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getInputStream()).thenReturn(servletInputStream("hello"));

        PathExpr path = new PathExpr("/persons/12/children");
        ActionRequest ar = new ActionRequestDefaultImpl(request, path);
        assertNotNull(ar);
        assertNotNull(ar.bodyAsString());

        verify(request).getInputStream();

    }
}
