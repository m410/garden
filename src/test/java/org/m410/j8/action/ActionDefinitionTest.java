package org.m410.j8.action;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.m410.j8.controller.Ctlr;
import org.m410.j8.controller.HttpMethod;
import org.m410.j8.mock.MockServletRequest;
import org.m410.j8.mock.MockServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class ActionDefinitionTest {
    Ctlr controller = () -> { return null; };

    @Test
    public void testDoesPathMatch() {
        HttpServletRequest request = new MockServletRequest() {
            @Override public String getRequestURI() { return "/"; }
            @Override public String getMethod() { return "GET"; }
        };
        Action a = (args) -> Response.response();
        ActionDefinition ad = new ActionDefinition(controller, a, new PathExpr(""), HttpMethod.GET);
        assertTrue(ad.doesRequestMatchAction(request));
    }

    @Test
    public void testDoesLongerPathMatch() {
        HttpServletRequest request = new MockServletRequest() {
            @Override public String getRequestURI() { return "/a/b/c"; }
            @Override public String getMethod() { return "GET"; }
        };
        Action a = (args) -> Response.response();
        ActionDefinition ad = new ActionDefinition(controller, a, new PathExpr("a/b/c"), HttpMethod.GET);
        assertTrue(ad.doesRequestMatchAction(request));
    }

    @Test
    public void testDoesLongerPathMatchWithExtension() {
        HttpServletRequest request = new MockServletRequest() {
            @Override public String getRequestURI() { return "/a/b/c.m410"; }
            @Override public String getMethod() { return "GET"; }
        };
        Action a = (args) -> Response.response();
        ActionDefinition ad = new ActionDefinition(controller, a, new PathExpr("a/b/c"), HttpMethod.GET);
        assertTrue(ad.doesRequestMatchAction(request));
    }

    @Test
    public void testApply() {
        HttpServletRequest request = new MockServletRequest() {
            @Override public String getRequestURI() { return "/"; }
        };

        HttpServletResponse response = new MockServletResponse();

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
        HttpServletRequest request = new MockServletRequest() {
            @Override public String getRequestURI() { return "/"; }
            @Override public String getMethod() { return "PUT"; }
        };

        HttpServletResponse response = new MockServletResponse();
        Action a = (args) -> { return Response.response(); };
        ActionDefinition ad1 = new ActionDefinition(controller, a,new PathExpr(""), HttpMethod.GET);
        ActionDefinition ad2 = new ActionDefinition(controller, a,new PathExpr(""), HttpMethod.PUT);

        assertTrue(ad2.doesRequestMatchAction(request));
        assertFalse(ad1.doesRequestMatchAction(request));
    }

}
