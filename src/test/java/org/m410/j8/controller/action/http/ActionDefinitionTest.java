package org.m410.j8.controller.action.http;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.m410.j8.controller.action.PathExpr;
import org.m410.j8.controller.Ctlr;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class ActionDefinitionTest {
    Ctlr controller = () -> { return null; };

    @Test
    public void testDoesPathMatch() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/");
        when(request.getMethod()).thenReturn("GET");

        Action a = (args) -> Response.response();
        ActionDefinition ad = new ActionDefinition(controller, a, new PathExpr(""), HttpMethod.GET);
        assertTrue(ad.doesRequestMatchAction(request));
    }

    @Test
    public void testDoesLongerPathMatch() {
        HttpServletRequest request = mock(HttpServletRequest.class) ;
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/a/b/c");
        when(request.getMethod()).thenReturn("GET");

        Action a = (args) -> Response.response();
        ActionDefinition ad = new ActionDefinition(controller, a, new PathExpr("a/b/c"), HttpMethod.GET);
        assertTrue(ad.doesRequestMatchAction(request));
    }

    @Test
    public void testDoesLongerPathMatchWithExtension() {
        HttpServletRequest request = mock(HttpServletRequest.class) ;
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/a/b/c.m410");
        when(request.getMethod()).thenReturn("GET");

        Action a = (args) -> Response.response();
        ActionDefinition ad = new ActionDefinition(controller, a, new PathExpr("a/b/c"), HttpMethod.GET);
        assertTrue(ad.doesRequestMatchAction(request));
    }

    @Test
    public void testApply() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/");
        when(request.getMethod()).thenReturn("GET");

        HttpServletResponse response = mock(HttpServletResponse.class);

        Action a = (args) -> {
            throw new RuntimeException("I was called");
        };

        try {
            ActionDefinition ad = new ActionDefinition(controller, a, new PathExpr(""), HttpMethod.GET);
            ad.apply(request, response);
            fail("exception was not thrown");
        }
        catch (Exception e) {
            assertNotNull(e);
        }
    }

    @Test
    public void actionDefinitionsEqual() {
        Action action = (args) -> { return null; };
        ActionDefinition ad1 = new ActionDefinition(controller, action,new PathExpr(""), HttpMethod.GET);
        ActionDefinition ad2 = new ActionDefinition(controller, action,new PathExpr(""), HttpMethod.GET);
        assertEquals(ad1,ad2);
    }

    @Test
    public void actionDefinitionsDontEqual() {
        Action action = (args) -> { return null; };
        ActionDefinition ad1 = new ActionDefinition(controller, action,new PathExpr(""), HttpMethod.GET);
        ActionDefinition ad2 = new ActionDefinition(controller, action,new PathExpr("persons"), HttpMethod.GET);
        assertNotEquals(ad1, ad2);
    }

    @Test @Ignore
    public void testMatchContentType() {
        assertTrue("Implement me", false);
    }

    @Test
    public void testMatchHttpMethod() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/");
        when(request.getMethod()).thenReturn("PUT");

        HttpServletResponse response = mock(HttpServletResponse.class);
        Action a = (args) -> Response.response();
        ActionDefinition ad1 = new ActionDefinition(controller, a,new PathExpr(""), HttpMethod.GET);
        ActionDefinition ad2 = new ActionDefinition(controller, a,new PathExpr(""), HttpMethod.PUT);

        assertTrue(ad2.doesRequestMatchAction(request));
        assertFalse(ad1.doesRequestMatchAction(request));
    }

}
