package org.m410.j8.action.status;

/**
 * Document Me..
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
