package org.m410.j8.action;

import javax.servlet.http.HttpSession;

/**
 * Still a work in progress.
 *
 * @author Michael Fortin
 */
public interface Flash {
    FlashSession forSession(HttpSession session);
}
