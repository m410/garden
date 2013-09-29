package us.m410.j8.action.direction;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
final public class Directions {
    public static NoView noView() {
        return new NoView();
    }

    public static View view(String s) {
        return new View(s);
    }

    public static Redirect redirect(String x) {
        return new Redirect(x);
    }
}
