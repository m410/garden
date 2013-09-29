package us.m410.j8.action.direction;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class Redirect implements Direction {
    private String path;

    public Redirect(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
