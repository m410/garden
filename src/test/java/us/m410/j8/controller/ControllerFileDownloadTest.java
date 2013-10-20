package us.m410.j8.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import us.m410.j8.action.Action;
import static us.m410.j8.action.Response.*;

import us.m410.j8.action.ActionDefinition;
import us.m410.j8.action.PathExpr;
import us.m410.j8.action.direction.Directions;
import us.m410.j8.mock.MockServletRequest;
import us.m410.j8.mock.MockServletResponse;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class ControllerFileDownloadTest {
    @Test
    public void testDownload() {
        HttpServletResponse response = new MockServletResponse() {
            @Override public ServletOutputStream getOutputStream() throws IOException {
                return new ServletOutputStream(){
                    @Override public boolean isReady() { return false; }
                    @Override public void setWriteListener(WriteListener writeListener) { }
                    @Override public void write(int b) throws IOException { }
                };
            }
        };
        MockServletRequest request = new MockServletRequest(){
            @Override public String getRequestURI() { return "/otherpath"; }
            @Override public String getMethod() { return "GET"; }
        };
        Action a = (req) ->{
            return response().contentType("application/json").stream(Assert::assertNotNull);
        };

        ActionDefinition ad = new ActionDefinition(a,new PathExpr(""), HttpMethod.GET);
        ad.apply(request, response);
    }
}
