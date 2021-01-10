package org.m410.garden.controller.action.status;

import com.google.common.collect.ImmutableList;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.m410.garden.controller.Controller;
import org.m410.garden.controller.Securable;
import org.m410.garden.controller.action.PathExpr;
import org.m410.garden.controller.action.http.Action;
import org.m410.garden.controller.action.http.HttpActionDefinition;
import org.m410.garden.controller.action.http.HttpMethod;
import org.m410.garden.controller.auth.AuthenticationProvider;
import org.m410.garden.controller.auth.Authorizable;
import org.m410.garden.zone.ZoneScope;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class ActionStatusTest {

    class FixtureController extends Controller implements Securable, Authorizable {
        final List<String> roles = ImmutableList.of("ADMIN");
        final Ssl secure = Ssl.Only;

        public FixtureController() { super(""); }

        @Override public List<String> acceptRoles() { return roles; }
        @Override public List<? extends HttpActionDefinition> actions() { return ImmutableList.of(); }
    }

    FixtureController controller = new FixtureController();



    @Test
    public void testActOn() {
        Action action = (args) -> null;
        final HttpMethod get = HttpMethod.GET;
        final PathExpr path = new PathExpr("/path");
        HttpActionDefinition ad = new HttpActionDefinition(controller,action, path, get,
                Securable.Ssl.Optional, new String[]{}, new String[]{}, ZoneScope.None);

        HttpSession httpSession = mock(HttpSession.class);
        when(httpSession.getAttribute(AuthenticationProvider.SESSION_KEY)).thenReturn("anything");

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/path.m410");
        when(request.getMethod()).thenReturn("GET");
        when(request.getSession(false)).thenReturn(httpSession);
        when(request.getSession(false)).thenReturn(httpSession);

        Principal principal = mock(Principal.class);
        when(request.getUserPrincipal()).thenReturn(principal);

        assertEquals(ActOn.getInstance(), ad.status(request));
    }

    @Test
    public void testForward() {
        Action action = (args) -> { return null; };
        final HttpMethod get = HttpMethod.GET;
        final PathExpr path = new PathExpr("/path");
        HttpActionDefinition ad = new HttpActionDefinition(controller,action, path, get,
                Securable.Ssl.Optional, new String[]{}, new String[]{}, ZoneScope.None);

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
        HttpActionDefinition ad = new HttpActionDefinition(controller,action, path, get,
                Securable.Ssl.Optional, new String[]{}, new String[]{}, ZoneScope.None);

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
                Securable.Ssl.Only, new String[]{}, new String[]{}, ZoneScope.None);

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
                Securable.Ssl.Optional, new String[]{}, new String[]{}, ZoneScope.None);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/path.m410");
        when(request.getSession(false)).thenReturn(null);

        final RedirectToAuth expected = new RedirectToAuth("/","/path");
        assertEquals(expected, ad.status(request));

        verify(request).getSession(false);
        verify(request).getContextPath();
        verify(request,times(3)).getRequestURI();
    }

    @Test
    public void notAnAction() {
        final Action action = (args) -> { return null; };
        final HttpMethod get = HttpMethod.GET;
        final PathExpr path = new PathExpr("/path");
        HttpActionDefinition ad = new HttpActionDefinition(controller,action, path, get,
                Securable.Ssl.Optional, new String[]{}, new String[]{}, ZoneScope.None);


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
                Securable.Ssl.Optional, new String[]{"USER"}, new String[]{}, ZoneScope.None);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/path.m410");

        final Forbidden expected = Forbidden.getInstance();
        assertEquals(expected, ad.status(request));

        verify(request).getContextPath();
        verify(request,times(2)).getRequestURI();
    }

}
