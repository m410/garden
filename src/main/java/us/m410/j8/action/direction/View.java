package us.m410.j8.action.direction;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class View implements Direction {
    private String path;

    public View(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
