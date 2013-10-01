package us.m410.j8.action.direction;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public interface Direction {
    public static final int  NO_VIEW = 0;
    public static final int  VIEW = 1;
    public static final int  REDIRECT = 2;
    public static final int  FORWARD = 3;
    public static final int  ERROR = 4;
    public static final int  FORBIDDEN = 5;

    int id();
}
