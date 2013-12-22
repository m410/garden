package org.m410.j8.controller.action.status;

/**
 * An action that is forbibben to be accessed.
 *
 * @author Michael Fortin
 */
public final class Forbidden implements ActionStatus {
    private static final Forbidden instance = new Forbidden();

    public static Forbidden getInstance() {
        return instance;
    }

    @Override
    public int id() {
        return FORBIDDEN;
    }
}
