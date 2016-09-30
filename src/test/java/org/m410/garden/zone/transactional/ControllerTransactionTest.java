package org.m410.garden.zone.transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.m410.garden.controller.MockServletInput;
import org.m410.garden.servlet.M410Filter;
import org.m410.garden.zone.transactional.fixtures.TrxApplication;
import org.m410.garden.zone.transactional.fixtures.Trx;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @author m410
 */
@RunWith(JUnit4.class)
public class ControllerTransactionTest implements MockServletInput {
    TrxApplication app;
    HttpServletRequest request;
    HttpServletResponse response;

    @Before
    public void setup() {
        app = new TrxApplication();
        app.init(null);
        Trx.resetCount();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
    }

    @Test
    public void testNoTransaction() throws Exception {
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/");
        when(request.getMethod()).thenReturn("GET");

        StringBuffer buffer = new StringBuffer();
        when(response.getOutputStream()).thenReturn(servletOutputStream(buffer));

        app.doRequest(request,response);
        assertEquals(0, Trx.getCallCount());
        assertEquals("none",buffer.toString());
    }

    @Test
    public void testTransactionInService()  throws Exception {
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/service");
        when(request.getMethod()).thenReturn("GET");

        StringBuffer buffer = new StringBuffer();
        when(response.getOutputStream()).thenReturn(servletOutputStream(buffer));

        app.doRequest(request,response);
        assertEquals(1, Trx.getCallCount());
        assertEquals(" a b c", buffer.toString());
    }

    @Test
    public void testTransactionWithActionAndService()  throws Exception {
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/action-service");
        when(request.getMethod()).thenReturn("GET");

        StringBuffer buffer = new StringBuffer();
        when(response.getOutputStream()).thenReturn(servletOutputStream(buffer));

        app.doRequest(request,response);
        assertEquals(2, Trx.getCallCount());
        assertEquals(" a b c", buffer.toString());
    }

    @Test
    public void testTransactionInAction()  throws Exception {
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/action");
        when(request.getMethod()).thenReturn("GET");

        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/some/some")).thenReturn(dispatcher);

        app.doRequest(request, response);
        assertEquals(1, Trx.getCallCount());
    }

    @Test
    public void testTransactionInActionAndView()  throws Exception {
        M410Filter filter = new M410Filter();

        ServletContext context = mock(ServletContext.class);
        when(context.getAttribute("application")).thenReturn(app);

        when(request.getServletContext()).thenReturn(context);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/action-view.m410");
        when(request.getMethod()).thenReturn("GET");

        FilterChain chain = mock(FilterChain.class);

        filter.doFilter(request, response, chain);
        assertEquals(1, Trx.getCallCount());
    }

    @Test
    public void noTransactionWithAction()  throws Exception {
        M410Filter filter = new M410Filter();

        ServletContext context = mock(ServletContext.class);
        when(context.getAttribute("application")).thenReturn(app);

        when(request.getServletContext()).thenReturn(context);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/.m410");
        when(request.getMethod()).thenReturn("GET");

        FilterChain chain = mock(FilterChain.class);

        filter.doFilter(request, response, chain);
        assertEquals(0, Trx.getCallCount());
    }
}
