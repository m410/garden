package org.m410.garden.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.m410.garden.controller.action.http.Action;

import org.m410.garden.controller.action.http.HttpActionDefinition;
import org.m410.garden.controller.action.PathExpr;
import org.m410.garden.controller.action.http.HttpMethod;
import org.m410.garden.transaction.TransactionScope;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.m410.garden.controller.action.http.Response.*;


/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class ControllerFileDownloadTest implements MockServletInput {

    HttpCtrl controller = () -> { return null; };


    @Test
    public void testDownload() throws Exception {
        HttpServletResponse response = mock(HttpServletResponse.class);
        final StringBuffer sb = new StringBuffer();
        when(response.getOutputStream()).thenReturn(servletOutputStream(sb));

        HttpServletRequest request = mock(HttpServletRequest.class);

        Action a = (req) -> respond()
                .withContentType("application/json")
                .asStream((out) -> out.write("hi".getBytes()));

        HttpActionDefinition ad = new HttpActionDefinition(controller, a,new PathExpr(""), HttpMethod.GET,
                Securable.State.Optional, new String[]{},new String[]{}, TransactionScope.None);
        ad.apply(request, response);

        verify(response).getOutputStream();
        assertEquals("hi", sb.toString());
    }
}
