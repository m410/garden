package org.m410.j8.action;

import javax.servlet.http.HttpSession;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public interface Flash {
    FlashSession forSession(HttpSession session);
}
