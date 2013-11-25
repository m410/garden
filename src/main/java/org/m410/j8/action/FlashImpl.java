package org.m410.j8.action;

import javax.servlet.http.HttpSession;

/**
 * Still a work in progress.
 *
 * @author Michael Fortin
 */
public class FlashImpl implements Flash {
    String message;

    public FlashImpl(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public FlashSession forSession(HttpSession session) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
