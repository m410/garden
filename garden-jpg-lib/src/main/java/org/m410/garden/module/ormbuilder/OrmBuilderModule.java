package org.m410.garden.module.ormbuilder;

import org.m410.garden.module.ormbuilder.orm.EntityFactory;
import org.m410.garden.module.ormbuilder.orm.OrmXmlBuilder;

import java.util.List;

/**
 * An Application Module that adds build time functionality to the application
 * to make the orm.xml and persistence.xml files.
 *
 * @author Michael Fortin
 */
public interface OrmBuilderModule {

    /**
     * the entity builder instances for the application.  These factories generate the
     * orm.xml file for each entity.
     *
     * @return a list of EntityFactories
     */
    List<? extends EntityFactory> entityBuilders();

    /**
     * Creates the builder with the injected application configuration.
     *
     * @return the builder for the application.
     */
    default OrmXmlBuilder configureBuilder() {
        OrmXmlBuilder builder = new OrmXmlBuilder();
        entityBuilders().forEach(e -> builder.addEntity(e.makeEntity()));
        return builder;
    }
    //
    //    default PersistenceUnitBuilder configurePersistence() {
//        // todo fix hard coded values
//        return new PersistenceXmlBuilder()
//                .name("demo")
//                .description("Sample persistence mapping")
//                .mappingFile("org.m410.demo")
//                .transactionType("RESOURCE_LOCAL")
//                .property("sampleName","sampleValue");
//    }
}
