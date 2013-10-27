package org.m410.j8.action.status;

/**
 * Document Me..
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
