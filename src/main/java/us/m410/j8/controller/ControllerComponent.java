package us.m410.j8.controller;

import us.m410.j8.configuration.Configuration;

import java.util.List;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public interface ControllerComponent {
    List<? extends Controller> makeControllers(Configuration c);
}
