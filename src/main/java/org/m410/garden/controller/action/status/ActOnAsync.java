package org.m410.garden.controller.action.status;

/**
 * An async request action that gets acted on.
 *
 * @author Michael Fortin
 */
public final class ActOnAsync implements ActionStatus {
    private static final ActOnAsync instance = new ActOnAsync();

    public static ActOnAsync getInstance() {
        return instance;
    }

    @Override
    public int id() {
        return ACT_ON_ASYNC;
    }
}
