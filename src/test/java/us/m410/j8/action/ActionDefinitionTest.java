package us.m410.j8.action;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import us.m410.j8.action.direction.Directions;
import us.m410.j8.controller.HttpMethod;
import us.m410.j8.mock.MockServletRequest;
import us.m410.j8.mock.MockServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;

/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class ActionDefinitionTest {

    @Test
    public void testDoesPathMatch() {
        HttpServletRequest request = new MockServletRequest() {
            @Override public String getRequestURI() { return "/"; }
            @Override public String getMethod() { return "GET"; }
        };
        Action a = (args) -> { return Response.response(); };
        ActionDefinition ad = new ActionDefinitionImpl(a,new PathExpr(""), HttpMethod.GET, Directions.noView());
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
            ActionDefinition ad = new ActionDefinitionImpl(a,new PathExpr(""), HttpMethod.GET, Directions.noView());
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
        ActionDefinition ad1 = new ActionDefinitionImpl(action,new PathExpr(""), HttpMethod.GET, Directions.noView());
        ActionDefinition ad2 = new ActionDefinitionImpl(action,new PathExpr(""), HttpMethod.GET, Directions.noView());
        assertEquals(ad1,ad2);
    }

    @Test
    public void actionDefinitionsDontEqual() {
        Action action = (args) -> { return null; };
        ActionDefinition ad1 = new ActionDefinitionImpl(action,new PathExpr(""), HttpMethod.GET, Directions.noView());
        ActionDefinition ad2 = new ActionDefinitionImpl(action,new PathExpr("persons"), HttpMethod.GET, Directions.noView());
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
        ActionDefinition ad1 = new ActionDefinitionImpl(a,new PathExpr(""), HttpMethod.GET, Directions.noView());
        ActionDefinition ad2 = new ActionDefinitionImpl(a,new PathExpr(""), HttpMethod.PUT, Directions.noView());

        assertTrue(ad2.doesRequestMatchAction(request));
        assertFalse(ad1.doesRequestMatchAction(request));
    }

}
