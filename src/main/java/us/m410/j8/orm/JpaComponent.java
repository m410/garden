package us.m410.j8.orm;

import com.google.common.collect.ImmutableList;
import us.m410.j8.configuration.Configuration;
import us.m410.j8.service.ThreadLocalComponent;

import java.util.List;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public interface JpaComponent extends ThreadLocalComponent {

    default List<? extends ThreadLocalFactory> threadLocalFactories(Configuration c) {
        return ImmutableList.of(
                new EntityManagerFactoryFactory(c)
        );
    }
}
