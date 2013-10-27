package org.m410.j8.action;

import javax.servlet.ServletException;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class RuntimeServletException extends RuntimeException {
    public RuntimeServletException(ServletException e) {
        super(e);
    }
}
