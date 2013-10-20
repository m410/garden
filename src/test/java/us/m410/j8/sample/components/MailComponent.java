package us.m410.j8.sample.components;

import com.google.common.collect.ImmutableList;
import us.m410.j8.application.ApplicationComponent;
import us.m410.j8.configuration.Configuration;

import java.util.List;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public interface MailComponent extends ApplicationComponent {
    default List<?> makeServices(Configuration c) {
        return ImmutableList.of(new MailService());
    }
}
