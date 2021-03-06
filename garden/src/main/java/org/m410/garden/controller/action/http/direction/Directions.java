package org.m410.garden.controller.action.http.direction;

/**
 * Static helpers to create directions.
 *
 * @author Michael Fortin
 */
final public class Directions {
    static private NoView defaultNoView = new NoView();

    public static NoView noView() {
        return defaultNoView;
    }

    public static View view(String s) {
        return new View(s);
    }

    public static Redirect redirect(String x) {
        return new Redirect(x);
    }
}
