package us.m410.j8.action.status;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import us.m410.j8.action.Action;
import us.m410.j8.action.ActionDefinition;
import us.m410.j8.action.ActionDefinitionImpl;
import us.m410.j8.action.PathExpr;
import us.m410.j8.action.direction.Directions;
import us.m410.j8.action.direction.NoView;
import us.m410.j8.action.status.*;
import us.m410.j8.controller.HttpMethod;
import us.m410.j8.mock.MockServletRequest;

import static org.junit.Assert.*;


/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class ActionStatusTest {

    @Test
    public void testActOn() {
        Action action = (args) -> { return null; };
        final NoView view = Directions.noView();
        final HttpMethod get = HttpMethod.GET;
        final PathExpr path = new PathExpr("/path");
        ActionDefinition ad = new ActionDefinitionImpl(action, path, get, view);
        MockServletRequest request = new MockServletRequest(){
            @Override public String getRequestURI() { return "/path"; }
            @Override public String getMethod() { return "GET"; }
        };
        assertEquals(ActOn.getInstance(), ad.status(request));
    }

    @Test @Ignore
    public void testActOnAsync() {
        final Action action = (args) -> { return null; };
        final NoView view = Directions.noView();
        final HttpMethod get = HttpMethod.GET;
        final PathExpr path = new PathExpr("/path");
        ActionDefinition ad = new ActionDefinitionImpl(action, path, get, view);
        MockServletRequest request = new MockServletRequest(){
            @Override public String getRequestURI() { return "/path"; }
            @Override public String getMethod() { return "GET"; }
        };
        assertEquals(ActOnAsync.getInstance(), ad.status(request));
    }

    @Test
    public void redirectToSecure() {
        final Action action = (args) -> { return null; };
        final NoView view = Directions.noView();
        final HttpMethod get = HttpMethod.GET;
        final PathExpr path = new PathExpr("/path");
        ActionDefinition ad = new ActionDefinitionImpl(action, path, get, view, true, false,false, new String[]{});
        MockServletRequest request = new MockServletRequest(){
            @Override public String getRequestURI() { return "/path"; }
            @Override public String getMethod() { return "GET"; }
            @Override public boolean isSecure() { return false; }
        };
        final RedirectToSecure expected = new RedirectToSecure("/");
        assertEquals(expected, ad.status(request));
    }

    @Test
    public void redirectToAuth() {
        final Action action = (args) -> { return null; };
        final NoView view = Directions.noView();
        final HttpMethod get = HttpMethod.GET;
        final PathExpr path = new PathExpr("/path");
        ActionDefinition ad = new ActionDefinitionImpl(action, path, get, view, false, true, false, new String[]{});
        MockServletRequest request = new MockServletRequest(){
            @Override public String getRequestURI() { return "/path"; }
            @Override public String getMethod() { return "GET"; }
        };
        final RedirectToAuth expected = new RedirectToAuth("/","/path");
        assertEquals(expected, ad.status(request));
    }

    @Test @Ignore
    public void dispatchTo() {
        Action action = (args) -> { return null; };
        ActionDefinition ad = new ActionDefinitionImpl(action,new PathExpr(""), HttpMethod.GET, Directions.noView());
        MockServletRequest request = new MockServletRequest();
        final DispatchTo expected = new DispatchTo("/");
        assertEquals(expected, ad.status(request));
    }

    @Test
    public void notAnAction() {
        final Action action = (args) -> { return null; };
        final NoView view = Directions.noView();
        final HttpMethod get = HttpMethod.GET;
        final PathExpr path = new PathExpr("/path");
        ActionDefinition ad = new ActionDefinitionImpl(action, path, get, view);
        MockServletRequest request = new MockServletRequest(){
            @Override public String getRequestURI() { return "/otherpath"; }
            @Override public String getMethod() { return "GET"; }
        };
        final NotAnAction expected = NotAnAction.getInstance();
        assertEquals(expected, ad.status(request));
    }

    @Test
    public void forbidden() {
        final Action action = (args) -> { return null; };
        final NoView view = Directions.noView();
        final HttpMethod get = HttpMethod.GET;
        final PathExpr path = new PathExpr("/path");
        ActionDefinition ad = new ActionDefinitionImpl(action, path, get, view);
        MockServletRequest request = new MockServletRequest(){
            @Override public String getRequestURI() { return "/otherpath"; }
            @Override public String getMethod() { return "GET"; }
        };
        final Forbidden expected = Forbidden.getInstance();
        assertEquals(expected, ad.status(request));
    }

}
