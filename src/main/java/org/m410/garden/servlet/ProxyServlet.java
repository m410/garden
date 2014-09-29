package org.m410.garden.servlet;

import javax.servlet.Servlet;

/**
 * @author m410
 */
public interface ProxyServlet extends Servlet {
    void setDelegateName(String name);
}
