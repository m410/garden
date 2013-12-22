package org.m410.j8.controller.action.flash;

import javax.servlet.http.HttpSession;

/**
 * Placed into session scope, this removes itself from the session once it's
 * called from a page.
 *
 * @author Michael Fortin
 */
public class FlashSession {
    private HttpSession httpSession;
    private String message;

    public FlashSession(String message, HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public String getMessage() {
        return message;
    }
}
