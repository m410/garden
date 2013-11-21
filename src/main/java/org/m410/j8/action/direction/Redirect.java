package org.m410.j8.action.direction;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public final class Redirect implements Direction {
    private String path;

    public Redirect(String path) {
        assert(path != null);
        this.path = path;
    }

    @Override
    public int id() {
        return REDIRECT;
    }

    public static Redirect to(String to) {
        return new Redirect(to);
    }

    public String getPath() {
        return path;
    }
}
