package us.m410.j8.action.status;

/**
 * Document Me..
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
