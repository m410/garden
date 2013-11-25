package org.m410.j8.action.status;

/**
 * Requests that are not actions.
 *
 * @author Michael Fortin
 */
public final class NotAnAction implements ActionStatus {
    private static final NotAnAction instance = new NotAnAction();

    public static NotAnAction getInstance() {
        return instance;
    }

    @Override
    public int id() {
        return NOT_AN_ACTION;
    }
}
