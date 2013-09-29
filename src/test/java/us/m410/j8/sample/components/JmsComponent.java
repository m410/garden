package us.m410.j8.sample.components;

import com.google.common.collect.ImmutableList;
import us.m410.j8.configuration.Configuration;
import us.m410.j8.service.ServiceComponent;

import java.util.List;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public interface JmsComponent extends ServiceComponent {
    default List<?> makeServices(Configuration c) {
        return ImmutableList.of();
    }
}
