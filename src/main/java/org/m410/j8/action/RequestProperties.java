package org.m410.j8.action;

import javax.servlet.http.HttpServletRequest;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class RequestProperties {
    private HttpServletRequest servletRequest;

    RequestProperties(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }


}
