package org.m410.j8.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.m410.j8.controller.action.http.Action;

import org.m410.j8.controller.action.http.ActionDefinition;
import org.m410.j8.controller.action.PathExpr;
import org.m410.j8.controller.action.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.m410.j8.controller.action.http.Response.*;


/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class ControllerFileDownloadTest implements MockServletInput {

    Ctlr controller = () -> { return null; };


    @Test
    public void testDownload() throws IOException {
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(response.getOutputStream()).thenReturn(servletOutputStream(new StringBuffer()));

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/otherpath");
        when(request.getMethod()).thenReturn("GET");

        Action a = (req) -> response().withContentType("application/json").asStream(Assert::assertNotNull);

        ActionDefinition ad = new ActionDefinition(controller, a,new PathExpr(""), HttpMethod.GET);
        ad.apply(request, response);
    }
}
