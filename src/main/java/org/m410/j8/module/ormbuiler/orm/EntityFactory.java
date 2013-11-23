package org.m410.j8.module.ormbuiler.orm;

/**
 * Creates an Entity object that will be embedded in the orm.xml.  Typically
 * a dao service or an interface that is implemented by the dao service class
 * will implement this.  In the case of an interface you may implement the it
 * as a default method and add it then add it to you service.  Once you've
 * defined the orm mapping it needs to be added to the application with
 * the OrmBuilderModule so it can be picked up by the build process to generate
 * the orm.xml
 *
 * @author Michael Fortin
 */
public interface EntityFactory {

    /**
     * Create an entity for inclusion in the orm.xml file.
     *
     * @return a Mapped entity bean.
     */
    Entity makeEntity();
}
