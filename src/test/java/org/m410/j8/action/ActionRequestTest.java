package org.m410.j8.action;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.m410.j8.controller.MockServletInput;

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
    }

    @Test
    public void getSession() {
        HttpSession session = mock(HttpSession.class);
        Hashtable<String,String> map = new Hashtable();
        map.put("name","value");
        when(session.getAttributeNames()).thenReturn(map.keys());
        when(session.getAttribute("name")).thenReturn("value");

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getSession(false)).thenReturn(session);

        PathExpr path = new PathExpr("/persons/12/children");
        ActionRequest ar = new ActionRequestDefaultImpl(request, path);
        assertNotNull(ar);
        assertEquals(1, ar.session().size());
    }


    @Test
    public void requestHeaders() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Hashtable<String,String> map = new Hashtable();
        map.put("name","value");
        when(request.getHeaderNames()).thenReturn(map.keys());
        when(request.getHeader("name")).thenReturn("value");
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/persons/12/children");

        PathExpr path = new PathExpr("/persons/12/children");
        ActionRequest ar = new ActionRequestDefaultImpl(request, path);
        assertNotNull(ar);
        assertEquals(1, ar.requestHeaders().size());
    }

    @Test
    public void urlParameters() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/persons/21/address");
        when(request.getMethod()).thenReturn("GET");

        PathExpr path = new PathExpr("/persons/{id}/children");
        ActionRequest ar = new ActionRequestDefaultImpl(request, path);
        assertNotNull(ar);
        assertEquals(1, ar.urlParameters().size());
    }

    @Test
    public void requestParameters() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Map<String,String[]> map = new HashMap();
        map.put("one",new String[]{"two"});
        when(request.getParameterMap()).thenReturn(map);

        PathExpr path = new PathExpr("/persons/12/children");
        ActionRequest ar = new ActionRequestDefaultImpl(request, path);
        assertNotNull(ar);
        assertEquals(1, ar.requestParameters().size());
    }

    @Test
    public void  userPrincipal() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        PathExpr path = new PathExpr("/persons/12/children");
        ActionRequest ar = new ActionRequestDefaultImpl(request, path);
        assertNotNull(ar);
        assertNotNull(ar.userPrincipal());
    }

    @Test
    public void postBodyAsStream() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getInputStream()).thenReturn(servletInputStream("hello"));

        PathExpr path = new PathExpr("/persons/12/children");
        ActionRequest ar = new ActionRequestDefaultImpl(request, path);
        assertNotNull(ar);
        assertNotNull(ar.postBodyAsStream());
    }

    @Test
    public void postBodyAsString() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getInputStream()).thenReturn(servletInputStream("hello"));

        PathExpr path = new PathExpr("/persons/12/children");
        ActionRequest ar = new ActionRequestDefaultImpl(request, path);
        assertNotNull(ar);
        assertNotNull(ar.postBodyAsString());
    }
}
