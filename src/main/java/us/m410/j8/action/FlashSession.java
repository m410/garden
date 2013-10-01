package us.m410.j8.action;

import javax.servlet.http.HttpSession;

/**
 * Document Me..
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
