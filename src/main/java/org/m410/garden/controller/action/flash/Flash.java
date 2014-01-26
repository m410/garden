package org.m410.garden.controller.action.flash;

import javax.servlet.http.HttpSession;

/**
 * Still a work in progress.
 *
 * @author Michael Fortin
 */
public interface Flash {
    FlashSession forSession(HttpSession session);
}
