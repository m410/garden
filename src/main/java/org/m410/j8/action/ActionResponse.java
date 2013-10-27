package org.m410.j8.action;

import org.m410.j8.action.direction.Direction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 */
public interface ActionResponse {

    String getContentType();

    Map<String, String> getHeaders();

    Direction getDirection();

    Map<String, Object> getModel();

    Map<String, Object> getSession();

    Flash getFlash();

    boolean doInvalidateSession();

    void handleResponse(HttpServletRequest request, HttpServletResponse response);
}
