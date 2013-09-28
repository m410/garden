package us.m410.j8.service;

import us.m410.j8.configuration.Configuration;
import us.m410.j8.orm.ThreadLocalFactory;

import java.util.List;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public interface ThreadLocalComponent {
    List<? extends ThreadLocalFactory> threadLocalFactories(Configuration c);
}
