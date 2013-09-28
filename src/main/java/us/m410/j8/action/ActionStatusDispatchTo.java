package us.m410.j8.action;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public final class ActionStatusDispatchTo implements ActionStatus {
    private String path;

    ActionStatusDispatchTo(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public int id() {
        return DISPATCH_TO;
    }
}
