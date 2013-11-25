package org.m410.j8.action.status;

/**
 * An action that is dispatched elsewhere.
 *
 * @author Michael Fortin
 */
public final class DispatchTo implements ActionStatus {
    private String path;

    DispatchTo(String path) {
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
