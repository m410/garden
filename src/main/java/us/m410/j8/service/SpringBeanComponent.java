package us.m410.j8.service;

import com.google.common.collect.ImmutableList;
import us.m410.j8.configuration.Configuration;

import java.util.List;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public interface SpringBeanComponent extends ServiceComponent {
    default List<?> makeServices(Configuration c) {
        return ImmutableList.of();
    }
}
