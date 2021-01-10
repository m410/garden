package org.m410.garden.controller.action.http.direction;

/**
 * Send a response forward.
 *
 * @author Michael Fortin
 */
public final class Forward implements Direction {
    private String path;

    public Forward(String path) {
        assert(path != null);
        this.path = path;
    }

    @Override
    public int id() {
        return FORWARD;
    }

    public static Forward to(String to) {
        return new Forward(to);
    }

    public String getPath() {
        return path;
    }
}
