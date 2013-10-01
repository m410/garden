package us.m410.j8.action;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertTrue;

/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class ActionDefinitionTest {

    @Test
    public void testDoesPathMatch() {
        HttpServletRequest request = new MockServletRequest() {
            @Override public String getRequestURI() { return "/"; }
        };
        Action a = (args) -> { return null; };
        ActionDefinition ad = new ActionDefinitionImpl("",a,new PathExpr(""));
        assertTrue(ad.doesPathMatch(request));
    }

    @Test
    public void testApply() {
        HttpServletRequest request = new MockServletRequest() {
            @Override public String getRequestURI() { return "/"; }
        };

        HttpServletResponse response = new MockServletResponse();
        boolean wasCalled = false;

        Action a = (args) -> {
            return null;
        };

        ActionDefinition ad = new ActionDefinitionImpl("",a,new PathExpr(""));
        ad.apply(request,response);
        assertTrue(wasCalled);
    }

    @Test
    public void actionDefinitionsEqual() {
        assertTrue("Implement me", false);
    }

    @Test
    public void actionDefinitionsDontEqual() {
        assertTrue("Implement me", false);
    }

    @Test
    public void testMatchContentType() {
        assertTrue("Implement me", false);
    }

    @Test
    public void testMatchHttpMethod() {
        assertTrue("Implement me", false);
    }

}
