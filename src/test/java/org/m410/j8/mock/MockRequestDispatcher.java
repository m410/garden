package org.m410.j8.mock;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author m410
 */
public class MockRequestDispatcher implements RequestDispatcher {
    @Override
    public void forward(ServletRequest servletRequest, ServletResponse servletResponse)
            throws ServletException, IOException {

    }

    @Override
    public void include(ServletRequest servletRequest, ServletResponse servletResponse)
            throws ServletException, IOException {
    }
}
