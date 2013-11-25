package org.m410.j8.action.direction;

/**
 * The default response view, of no view.  This is used on
 * responses that don't need a direction like when the response
 * is a outputstream.
 *
 * @author Michael Fortin
 */
public final class NoView implements Direction {

    NoView() {
    }

    @Override
    public int id() {
        return NO_VIEW;
    }
}
