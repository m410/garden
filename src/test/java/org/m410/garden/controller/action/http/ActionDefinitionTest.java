package org.m410.garden.controller.action.http;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.m410.garden.controller.HttpCtrl;
import org.m410.garden.controller.Securable;
import org.m410.garden.controller.action.PathExpr;
import org.m410.garden.transaction.TransactionScope;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class ActionDefinitionTest {
    HttpCtrl controller = () -> { return null; };


    @Test
    public void testDoesPathMatch() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/");
        when(request.getMethod()).thenReturn("GET");

        Action a = (args) -> Response.respond();
        HttpActionDefinition ad = new HttpActionDefinition(controller, a, new PathExpr(""), HttpMethod.GET,
                Securable.Ssl.Optional, new String[]{},new String[]{}, TransactionScope.None);
        assertTrue(ad.doesRequestMatchAction(request));

        verify(request).getContextPath();
        verify(request).getRequestURI();
        verify(request).getMethod();
    }

    @Test
    public void testDoesLongerPathMatch() {
        HttpServletRequest request = mock(HttpServletRequest.class) ;
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/a/b/c");
        when(request.getMethod()).thenReturn("GET");

        Action a = (args) -> Response.respond();
        HttpActionDefinition ad = new HttpActionDefinition(controller, a, new PathExpr("a/b/c"), HttpMethod.GET,
                Securable.Ssl.Optional, new String[]{},new String[]{}, TransactionScope.None);
        assertTrue(ad.doesRequestMatchAction(request));

        verify(request).getContextPath();
        verify(request).getRequestURI();
        verify(request).getMethod();
    }

    @Test
    public void testDoesLongerPathMatchWithExtension() {
        HttpServletRequest request = mock(HttpServletRequest.class) ;
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/a/b/c.m410");
        when(request.getMethod()).thenReturn("GET");

        Action a = (args) -> Response.respond();
        HttpActionDefinition ad = new HttpActionDefinition(controller, a, new PathExpr("a/b/c"), HttpMethod.GET,
                Securable.Ssl.Optional, new String[]{},new String[]{}, TransactionScope.None);
        assertTrue(ad.doesRequestMatchAction(request));

        verify(request).getContextPath();
        verify(request).getRequestURI();
        verify(request).getMethod();
    }

    @Test
    public void testApply() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        Action a = (args) -> {
            throw new RuntimeException("I was called");
        };

        HttpActionDefinition ad = new HttpActionDefinition(controller, a, new PathExpr(""), HttpMethod.GET,
                Securable.Ssl.Optional, new String[]{},new String[]{}, TransactionScope.None);

        try {
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
        HttpActionDefinition ad1 = new HttpActionDefinition(controller, action,new PathExpr(""), HttpMethod.GET,
                Securable.Ssl.Optional, new String[]{},new String[]{}, TransactionScope.None);
        HttpActionDefinition ad2 = new HttpActionDefinition(controller, action,new PathExpr(""), HttpMethod.GET,
                Securable.Ssl.Optional, new String[]{},new String[]{}, TransactionScope.None);
        assertEquals(ad1,ad2);
    }

    @Test
    public void actionDefinitionsDontEqual() {
        Action action = (args) -> { return null; };
        HttpActionDefinition ad1 = new HttpActionDefinition(controller, action,new PathExpr(""), HttpMethod.GET,
                Securable.Ssl.Optional, new String[]{},new String[]{}, TransactionScope.None);
        HttpActionDefinition ad2 = new HttpActionDefinition(controller, action,new PathExpr("persons"), HttpMethod.GET,
                Securable.Ssl.Optional, new String[]{},new String[]{}, TransactionScope.None);
        assertNotEquals(ad1, ad2);
    }

    @Test
    public void testDoesntMatchContentType() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/c.m410");
        when(request.getMethod()).thenReturn("GET");
        when(request.getContentType()).thenReturn("text/html");

        Action action = (args) -> { return null; };
        HttpActionDefinition a = new HttpActionDefinition(controller, action,new PathExpr("/c"),
                HttpMethod.GET, Securable.Ssl.Optional, new String[]{"application/json"}, new String[0], TransactionScope.None);

        boolean match = a.doesRequestMatchAction(request);
        assertFalse(match);

        verify(request).getContentType();
        verify(request).getContextPath();
        verify(request).getRequestURI();
        verify(request).getMethod();
    }

    @Test
    public void testMatchContentType() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/c.m410");
        when(request.getMethod()).thenReturn("GET");
        when(request.getContentType()).thenReturn("application/json");

        Action action = (args) -> { return null; };
        HttpActionDefinition a = new HttpActionDefinition(controller, action,new PathExpr("/c"),
                HttpMethod.GET, Securable.Ssl.Optional, new String[]{"application/json"}, new String[0], TransactionScope.None);

        boolean match = a.doesRequestMatchAction(request);
        assertTrue(match);

        verify(request).getContentType();
        verify(request).getContextPath();
        verify(request).getRequestURI();
        verify(request).getMethod();
    }


    @Test
    public void testMatchHttpMethod() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/");
        when(request.getMethod()).thenReturn("PUT");

        HttpServletResponse response = mock(HttpServletResponse.class);
        Action a = (args) -> Response.respond();
        HttpActionDefinition ad1 = new HttpActionDefinition(controller, a,new PathExpr(""), HttpMethod.GET,
                Securable.Ssl.Optional, new String[]{},new String[]{}, TransactionScope.None);
        HttpActionDefinition ad2 = new HttpActionDefinition(controller, a,new PathExpr(""), HttpMethod.PUT,
                Securable.Ssl.Optional, new String[]{},new String[]{}, TransactionScope.None);

        assertTrue(ad2.doesRequestMatchAction(request));
        assertFalse(ad1.doesRequestMatchAction(request));


        verify(request,times(2)).getContextPath();
        verify(request,times(2)).getRequestURI();
        verify(request,times(2)).getMethod();
    }

}
