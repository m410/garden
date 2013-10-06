package us.m410.j8.servlet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import us.m410.j8.configuration.ApplicationDefinition;
import us.m410.j8.configuration.Configuration;
import us.m410.j8.mock.MockFilterChain;
import us.m410.j8.mock.MockServletContext;
import us.m410.j8.mock.MockServletRequest;
import us.m410.j8.mock.MockServletResponse;
import us.m410.j8.sample.MyWebApp;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;

/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class M410FilterTest {

    @Test
    public void testDoFilter() {
        M410Filter filter = new M410Filter();

        MockServletRequest request = new MockServletRequest() {
            @Override
            public ServletContext getServletContext() {
                return new MockServletContext() {
                    MyWebApp myWebApp = new MyWebApp(new Configuration() {
                        @Override
                        public ApplicationDefinition getApplication() {
                            return null;
                        }
                    });

                    @Override
                    public Object getAttribute(String s) {
                        if("application".equals(s)) {
                            return myWebApp;
                        }
                        else
                            return super.getAttribute(s);
                    }
                };
            }
        };
        MockServletResponse response = new MockServletResponse();
        MockFilterChain chain = new MockFilterChain();

        try {
            filter.doFilter(request,response,chain);
        }
        catch (IOException | ServletException e) {
            fail("Exception was thrown: " + e.getMessage());
        }
    }
}
