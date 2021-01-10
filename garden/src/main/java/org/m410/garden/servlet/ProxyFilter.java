package org.m410.garden.servlet;

import javax.servlet.Filter;

/**
 * @author m410
 */
public interface ProxyFilter extends Filter {

    void setDelegateName(String name);
}
