package org.m410.garden.controller.action.flash;

import javax.servlet.http.HttpSession;

/**
 * Still a work in progress.
 *
 * @author Michael Fortin
 */
public interface Flash {

    public static enum Type {
        Success,
        Warning,
        Alert,
        Error
    }

    FlashSession forSession(HttpSession session);
}
