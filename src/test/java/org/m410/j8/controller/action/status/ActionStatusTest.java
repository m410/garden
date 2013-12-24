package org.m410.j8.controller.action.status;

import com.google.common.collect.ImmutableList;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.m410.j8.controller.Authorizable;
import org.m410.j8.controller.Controller;
import org.m410.j8.controller.action.ActionDefinition;
import org.m410.j8.controller.action.http.Action;
import org.m410.j8.controller.action.http.HttpActionDefinition;
import org.m410.j8.controller.action.PathExpr;
import org.m410.j8.controller.Ctlr;
import org.m410.j8.controller.action.http.HttpMethod;
import org.m410.j8.controller.Securable;


import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class ActionStatusTest {

    class FixtureController extends Controller implements Securable, Authorizable {
        final List<String> roles = ImmutableList.of("ADMIN");
        final State secure = State.Only;

        public FixtureController() { super(""); }

        @Override public List<String> acceptRoles() { return roles; }
        @Override public State secureState() { return secure; }
        @Override public List<? extends ActionDefinition> actions() { return ImmutableList.of(); }
    }

    FixtureController controller = new FixtureController();



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
                Securable.State.Only, new String[]{}, new String[]{});

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/path.m410");
        when(request.isSecure()).thenReturn(false);

        final RedirectToSecure expected = new RedirectToSecure("/");
        assertEquals(expected, ad.status(request));

        verify(request).getContextPath();
        verify(request,times(3)).getRequestURI();
        verify(request).isSecure();
    }

    @Test
    public void redirectToAuth() {
        final Action action = (args) -> { return null; };
        final HttpMethod get = HttpMethod.GET;
        final PathExpr path = new PathExpr("/path");
        HttpActionDefinition ad = new HttpActionDefinition(controller,action, path, get,
                Securable.State.Optional, new String[]{}, new String[]{});


        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/path.m410");

        final RedirectToAuth expected = new RedirectToAuth("/","/path");
        assertEquals(expected, ad.status(request));

        verify(request).getContextPath();
        verify(request,times(3)).getRequestURI();
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

        final NotAnAction expected = NotAnAction.getInstance();
        assertEquals(expected, ad.status(request));

        verify(request).getContextPath();
        verify(request).getRequestURI();
    }

    // won't work until authorization is done
    @Test @Ignore
    public void forbidden() {
        final Action action = (args) -> { return null; };
        final HttpMethod get = HttpMethod.GET;
        final PathExpr path = new PathExpr("/path");
        HttpActionDefinition ad = new HttpActionDefinition(controller,action, path, get,
                Securable.State.Optional, new String[]{}, new String[]{});

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/path.m410");

        final Forbidden expected = Forbidden.getInstance();
        assertEquals(expected, ad.status(request));

        verify(request).getContextPath();
        verify(request,times(2)).getRequestURI();
    }

}
