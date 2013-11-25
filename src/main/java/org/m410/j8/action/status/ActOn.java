package org.m410.j8.action.status;

/**
 * An Action that gets acted on.
 *
 * @author Michael Fortin
 */
public final class ActOn implements ActionStatus {
    private static final ActOn instance = new ActOn();

    public static ActOn getInstance() {
        return instance;
    }

    @Override
    public int id() {
        return ACT_ON;
    }
}
