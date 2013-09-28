package us.m410.j8.service;

import us.m410.j8.configuration.Configuration;

import java.util.List;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public interface ServiceComponent {
    List<?> makeServices(Configuration c);
}
