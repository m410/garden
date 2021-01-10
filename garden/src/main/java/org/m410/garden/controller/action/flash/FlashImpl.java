package org.m410.garden.controller.action.flash;

import javax.servlet.http.HttpSession;

/**
 * Still a work in progress.
 *
 * @author Michael Fortin
 */
public class FlashImpl implements Flash {
    String message;
    String internationalKey;

    public FlashImpl(String message) {
        this.message = message;
    }

    public FlashImpl(String message, String internationalKey) {
        this.message = message;
        this.internationalKey = internationalKey;
    }

    public String getMessage() {
        return message;
    }

    public String getInternationalKey() {
        return internationalKey;
    }

    @Override
    public FlashSession forSession(HttpSession session) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
