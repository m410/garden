package org.m410.j8.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.m410.j8.controller.action.http.Action;

import org.m410.j8.controller.action.http.HttpActionDefinition;
import org.m410.j8.controller.action.PathExpr;
import org.m410.j8.controller.action.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.Assert.*;
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
        final StringBuffer sb = new StringBuffer();
        when(response.getOutputStream()).thenReturn(servletOutputStream(sb));

        HttpServletRequest request = mock(HttpServletRequest.class);

        Action a = (req) -> respond()
                .withContentType("application/json")
                .asStream((out) -> out.write("hi".getBytes()));

        HttpActionDefinition ad = new HttpActionDefinition(controller, a,new PathExpr(""), HttpMethod.GET);
        ad.apply(request, response);

        verify(response).getOutputStream();
        assertEquals("hi", sb.toString());
    }
}
