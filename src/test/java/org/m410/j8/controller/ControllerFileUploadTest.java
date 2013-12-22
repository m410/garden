package org.m410.j8.controller;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.m410.j8.application.ThreadLocalSessionFactory;
import org.m410.j8.configuration.Configuration;
import org.m410.j8.sample.MyWebApp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class ControllerFileUploadTest implements MockServletInput{
    FileUploadController controller = new FileUploadController();
    MyWebApp myApp;

    @Before
    public void setup() {
        myApp = new MyWebApp() {
            @Override public List<? extends Ctlr> makeControllers(Configuration c) {
                return ImmutableList.of(controller);
            }

            @Override
            public List<? extends ThreadLocalSessionFactory<?>> makeThreadLocalFactories(Configuration c) {
                return null;
            }
        };
        myApp.init(null);
    }

    @Test
    public void uploadFile() throws IOException {
        final String expected = "post text";

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/file");
        when(request.getMethod()).thenReturn("POST");

        assertEquals("/file",request.getRequestURI());

        when(request.getInputStream()).thenReturn(servletInputStream(expected));

        HttpServletResponse response = mock(HttpServletResponse.class);

        StringBuffer sb = new StringBuffer();
        when(response.getOutputStream()).thenReturn(servletOutputStream(sb));
        myApp.doRequest(request, response);

        verify(request,times(3)).getRequestURI();
        verify(request,times(2)).getMethod();

        verify(response).setContentType("text/plain");

    }
}
