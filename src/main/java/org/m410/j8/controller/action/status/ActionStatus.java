package org.m410.j8.controller.action.status;

/**
 * Action status values used internally to determine the state of the
 * action request.
 *
 * @author Michael Fortin
 */
public interface ActionStatus {
    public static final int ACT_ON = 1;
    public static final int ACT_ON_ASYNC = 2;
    public static final int REDIRECT_TO_SECURE = 3;
    public static final int REDIRECT_TO_AUTH = 4;
    public static final int FORWARD = 5;
    public static final int DISPATCH_TO = 6;
    public static final int NOT_AN_ACTION = 7;
    public static final int FORBIDDEN = 8;

    int id();


}
