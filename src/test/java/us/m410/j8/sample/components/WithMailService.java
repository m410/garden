package us.m410.j8.sample.components;

import us.m410.j8.service.WithService;

import java.util.List;

import static java.util.Arrays.asList;

/**
 */
public interface WithMailService extends WithService {
    default List<Object> services() {
        return asList((Object) new MailService());
    }
}
