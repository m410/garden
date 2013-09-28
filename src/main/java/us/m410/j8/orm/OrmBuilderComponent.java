package us.m410.j8.orm;

import us.m410.j8.configuration.Configuration;

import java.util.List;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public interface OrmBuilderComponent {

    List<? extends OrmGenerator> ormGenerators();

    default PersistenceUnitBuilder persistenceUnitBuilder(Configuration configuration) {
        return new PersistenceUnitBuilder();
    }
}
