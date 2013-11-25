package org.m410.j8.action;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.m410.j8.mock.MockServletRequest;
import org.m410.j8.mock.MockSession;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.Assert.*;

/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class ActionRequestTest {

    @Test
    public void isActiveSessionNot() {
        HttpServletRequest request = new MockServletRequest() ;
        PathExpr path = new PathExpr("/persons/12/children");
        ActionRequest ar = new ActionRequestDefaultImpl(request, path);
        assertNotNull(ar);
        assertFalse(ar.isActiveSession());
    }

    @Test
    public void isActiveSessionYes() {
        HttpServletRequest request = new MockServletRequest() {
            MockSession mockSession = new MockSession();
            @Override
            public HttpSession getSession(boolean is) {
                return mockSession;
            }
        };
        PathExpr path = new PathExpr("/persons/12/children");
        ActionRequest ar = new ActionRequestDefaultImpl(request, path);
        assertNotNull(ar);
        assertTrue(ar.isActiveSession());
    }

    @Test
    public void getSession() {
        HttpServletRequest request = new MockServletRequest() {
            MockSession mockSession = new MockSession();
            @Override
            public HttpSession getSession(boolean is) {
                return mockSession;
            }
        };
        PathExpr path = new PathExpr("/persons/12/children");
        ActionRequest ar = new ActionRequestDefaultImpl(request, path);
        assertNotNull(ar);
        assertEquals(1, ar.session().size());
    }


    @Test
    public void requestHeaders() {
        HttpServletRequest request = new MockServletRequest();
        PathExpr path = new PathExpr("/persons/12/children");
        ActionRequest ar = new ActionRequestDefaultImpl(request, path);
        assertNotNull(ar);
        assertEquals(1, ar.requestHeaders().size());
    }

    @Test
    public void urlParameters() {
        HttpServletRequest request = new MockServletRequest();
        PathExpr path = new PathExpr("/persons/{id}/children");
        ActionRequest ar = new ActionRequestDefaultImpl(request, path);
        assertNotNull(ar);
        assertEquals(1, ar.urlParameters().size());
    }

    @Test
    public void requestParameters() {
        HttpServletRequest request = new MockServletRequest();
        PathExpr path = new PathExpr("/persons/12/children");
        ActionRequest ar = new ActionRequestDefaultImpl(request, path);
        assertNotNull(ar);
        assertEquals(1, ar.requestParameters().size());
    }

    @Test
    public void  userPrincipal() {
        HttpServletRequest request = new MockServletRequest();
        PathExpr path = new PathExpr("/persons/12/children");
        ActionRequest ar = new ActionRequestDefaultImpl(request, path);
        assertNotNull(ar);
        assertNotNull(ar.userPrincipal());
    }

    @Test
    public void postBodyAsStream() {
        HttpServletRequest request = new MockServletRequest() {
            @Override public ServletInputStream getInputStream() throws IOException {
                StringReader sr = new StringReader("hello");
                return new ServletInputStream() {
                    @Override public boolean isFinished() { return true; }
                    @Override public boolean isReady()  {
                        try { return sr.ready(); }
                        catch (IOException e) { throw new NotAPostException(e); }
                    }
                    @Override public void setReadListener(ReadListener readListener) { }
                    @Override public int read() throws IOException {
                        return sr.read();
                    }
                };
            }
        };
        PathExpr path = new PathExpr("/persons/12/children");
        ActionRequest ar = new ActionRequestDefaultImpl(request, path);
        assertNotNull(ar);
        assertNotNull(ar.postBodyAsStream());
    }

    @Test
    public void postBodyAsString() {
        HttpServletRequest request = new MockServletRequest() {
            @Override public ServletInputStream getInputStream() throws IOException {
                StringReader sr = new StringReader("hello");
                return new ServletInputStream() {
                    @Override public boolean isFinished() { return true; }
                    @Override public boolean isReady()  {
                        try { return sr.ready(); }
                        catch (IOException e) { throw new NotAPostException(e); }
                    }
                    @Override public void setReadListener(ReadListener readListener) { }
                    @Override public int read() throws IOException {
                        return sr.read();
                    }
                };
            }
        };
        PathExpr path = new PathExpr("/persons/12/children");
        ActionRequest ar = new ActionRequestDefaultImpl(request, path);
        assertNotNull(ar);
        assertNotNull(ar.postBodyAsString());
    }
}
