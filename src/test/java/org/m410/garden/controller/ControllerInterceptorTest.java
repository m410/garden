package org.m410.garden.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.m410.garden.controller.action.http.HttpActionDefinition;
import org.m410.garden.controller.fixtures.JsonInterceptController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;

/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class ControllerInterceptorTest {

    @Test
    public void testIntercept() {
        HttpSession session = mock(HttpSession.class);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/");
        when(request.getContextPath()).thenReturn("");
        when(request.getSession()).thenReturn(session);

        HttpServletResponse response = mock(HttpServletResponse.class);

        JsonInterceptController ctlr = new JsonInterceptController();
        HttpActionDefinition ad = ctlr.actions().get(0);
        ad.apply(request, response);

        verify(request).getSession();
        verify(request).getRequestURI();
        verify(request).getContextPath();
        verify(response).setContentType("text/plain");
        verify(response).setStatus(200);
    }
}
