package org.m410.j8.action.direction;

/**
 * Document Me..
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
