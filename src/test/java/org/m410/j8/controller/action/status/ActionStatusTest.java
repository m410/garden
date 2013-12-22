package org.m410.j8.controller.action.status;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.m410.j8.controller.action.http.Action;
import org.m410.j8.controller.action.http.HttpActionDefinition;
import org.m410.j8.controller.action.PathExpr;
import org.m410.j8.controller.Ctlr;
import org.m410.j8.controller.action.http.HttpMethod;
import org.m410.j8.controller.Securable;


import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class ActionStatusTest {
    Ctlr controller = () -> { return null; };

    @Test
    public void testActOn() {
        Action action = (args) -> { return null; };
        final HttpMethod get = HttpMethod.GET;
        final PathExpr path = new PathExpr("/path");
        HttpActionDefinition ad = new HttpActionDefinition(controller,action, path, get);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/path.m410");
        when(request.getMethod()).thenReturn("GET");

        assertEquals(ActOn.getInstance(), ad.status(request));
    }

    @Test
    public void testForward() {
        Action action = (args) -> { return null; };
        final HttpMethod get = HttpMethod.GET;
        final PathExpr path = new PathExpr("/path");
        HttpActionDefinition ad = new HttpActionDefinition(controller,action, path, get);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/path");
        when(request.getMethod()).thenReturn("GET");

        assertEquals(new Forward("/path"), ad.status(request));
    }

    @Test @Ignore
    public void testActOnAsync() {
        final Action action = (args) -> { return null; };
        final HttpMethod get = HttpMethod.GET;
        final PathExpr path = new PathExpr("/path");
        HttpActionDefinition ad = new HttpActionDefinition(controller,action, path, get);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/path.m410");
        when(request.getMethod()).thenReturn("GET");
        assertEquals(ActOnAsync.getInstance(), ad.status(request));
    }

    @Test
    public void redirectToSecure() {
        final Action action = (args) -> { return null; };
        final HttpMethod get = HttpMethod.GET;
        final PathExpr path = new PathExpr("/path");
        HttpActionDefinition ad = new HttpActionDefinition(controller,action, path, get,
                Securable.State.Optional, false,false, new String[]{}, new String[]{});

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/path.m410");
        when(request.getMethod()).thenReturn("GET");
        when(request.isSecure()).thenReturn(false);

        final RedirectToSecure expected = new RedirectToSecure("/");
        assertEquals(expected, ad.status(request));
    }

    @Test
    public void redirectToAuth() {
        final Action action = (args) -> { return null; };
        final HttpMethod get = HttpMethod.GET;
        final PathExpr path = new PathExpr("/path");
        HttpActionDefinition ad = new HttpActionDefinition(controller,action, path, get,
                Securable.State.Optional, true, false, new String[]{}, new String[]{});


        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/path.m410");
        when(request.getMethod()).thenReturn("GET");

        final RedirectToAuth expected = new RedirectToAuth("/","/path");
        assertEquals(expected, ad.status(request));
    }

    @Test
    public void dispatchTo() {
        Action action = (args) -> { return null; };
        HttpActionDefinition ad = new HttpActionDefinition(controller,action,new PathExpr(""), HttpMethod.GET);
        HttpServletRequest request = mock(HttpServletRequest.class);
        final DispatchTo expected = new DispatchTo("/");
        assertEquals(expected, ad.status(request));
    }

    @Test
    public void notAnAction() {
        final Action action = (args) -> { return null; };
        final HttpMethod get = HttpMethod.GET;
        final PathExpr path = new PathExpr("/path");
        HttpActionDefinition ad = new HttpActionDefinition(controller,action, path, get);


        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/otherpath");
        when(request.getMethod()).thenReturn("GET");


        final NotAnAction expected = NotAnAction.getInstance();
        assertEquals(expected, ad.status(request));
    }

    @Test
    public void forbidden() {
        final Action action = (args) -> { return null; };
        final HttpMethod get = HttpMethod.GET;
        final PathExpr path = new PathExpr("/path");
        HttpActionDefinition ad = new HttpActionDefinition(controller,action, path, get,
                Securable.State.Optional, false, true, new String[]{}, new String[]{});


        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/path.m410");
        when(request.getMethod()).thenReturn("GET");


        final Forbidden expected = Forbidden.getInstance();
        assertEquals(expected, ad.status(request));
    }

}
