package org.m410.j8.mock;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class MockFilterChain implements FilterChain {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse)
            throws IOException, ServletException {
    }
}
