package org.m410.j8.persistence;

import org.m410.j8.configuration.Configuration;
import org.m410.j8.persistence.orm.EntityFactory;
import org.m410.j8.persistence.orm.OrmXmlBuilder;
import org.m410.j8.persistence.unit.PersistenceUnitBuilder;

import java.util.List;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public interface OrmBuilderComponent {

    List<? extends EntityFactory> entityBuilders();

    default OrmXmlBuilder configureBuilder(Configuration configuration) {
        OrmXmlBuilder builder = new OrmXmlBuilder();
        entityBuilders().stream().forEach(e->builder.addEntity(e.makeEntity()));
        return builder;
    }

    default PersistenceUnitBuilder configurePersistence(Configuration configuration) {
        // todo fix hard coded values
        return new PersistenceUnitBuilder()
                .name("demo")
                .description("Sample persistence mapping")
                .mappingFile("org.m410.demo")
                .transactionType("RESOURCE_LOCAL")
                .property("sampleName","sampleValue");
    }
}
