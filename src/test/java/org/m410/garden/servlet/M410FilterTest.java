package org.m410.garden.servlet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.m410.garden.controller.Securable;
import org.m410.garden.controller.action.http.HttpActionDefinition;
import org.m410.garden.controller.action.PathExpr;
import org.m410.garden.application.Application;
import org.m410.garden.controller.action.http.HttpMethod;
import org.m410.garden.fixtures.MyWebApp;
import org.m410.garden.zone.transaction.TransactionScope;


import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class M410FilterTest {

    @Test
    public void testForward() throws ServletException, IOException {
        Application application = new MyWebApp() {
            @Override public Optional<HttpActionDefinition> actionForRequest(HttpServletRequest request) {
                return Optional.of(new HttpActionDefinition(null, null,new PathExpr("json"), HttpMethod.GET,
                        Securable.Ssl.Optional, new String[]{},new String[]{}, TransactionScope.None));
            }
        };

        ServletContext context = mock(ServletContext.class);
        when(context.getAttribute("application")).thenReturn(application);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getServletContext()).thenReturn(context);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/json");

        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/json.m410")).thenReturn(dispatcher);

        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        M410Filter filter = new M410Filter();
        filter.doFilter(request, response, chain);


        verify(request).getServletContext();
        verify(request).getContextPath();
        verify(request,times(4)).getRequestURI();

        verify(request).getRequestDispatcher("/json.m410");
    }
}
