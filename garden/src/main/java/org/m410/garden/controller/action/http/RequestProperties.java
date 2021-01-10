package org.m410.garden.controller.action.http;

import javax.servlet.http.HttpServletRequest;

/**
 * Makes the request properties accessible from the ActionRequest object.
 *
 * todo finish me...
 * @author Michael Fortin
 */
public class RequestProperties {
    private HttpServletRequest servletRequest;

    RequestProperties(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }


}
