package us.m410.j8.persistence.orm;

import us.m410.j8.configuration.Configuration;

import java.util.List;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public interface OrmConfigBuilder {
    List<? extends EntityFactory> entities();

    default OrmXmlBuilder orm(Configuration configuration) {
        return null;
    }
}
