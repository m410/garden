package org.m410.j8.servlet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.m410.j8.action.ActionDefinition;
import org.m410.j8.action.PathExpr;
import org.m410.j8.application.Application;
import org.m410.j8.controller.HttpMethod;
import org.m410.j8.mock.*;
import org.m410.j8.sample.MyWebApp;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class M410FilterTest {

    @Test
    public void testForward() throws ServletException, IOException {
        Application application = new MyWebApp() {
            @Override public Optional<ActionDefinition> actionForRequest(HttpServletRequest request) {
                return Optional.of(new ActionDefinition(null, null,new PathExpr("json"), HttpMethod.GET));
            }
        };

        MockServletContext context = new MockServletContext() {
            @Override public Object getAttribute(String s) {
                if("application".equals(s))
                    return application;
                else
                    return null;
            }
        };

        MockServletRequest request = new MockServletRequest() {
            @Override public ServletContext getServletContext() { return context; }
            @Override public String getRequestURI() { return "/json"; }
            @Override public String getMethod() { return "GET"; }
            @Override public RequestDispatcher getRequestDispatcher(String s) {
                if("/json.m410".equals(s))
                    return new MockRequestDispatcher();
                else
                    return null;
            }
        };

        MockServletResponse response = new MockServletResponse();
        MockFilterChain chain = new MockFilterChain();

        M410Filter filter = new M410Filter();
        filter.doFilter(request, response, chain);
    }
}
