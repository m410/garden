package us.m410.j8.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 */
public interface ActionDefinition {

    boolean doesRequestMatchAction(HttpServletRequest req);

    void apply(HttpServletRequest request, HttpServletResponse response);

    ActionStatus status(HttpServletRequest request);
}
