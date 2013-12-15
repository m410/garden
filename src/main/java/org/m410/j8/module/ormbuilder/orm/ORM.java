package org.m410.j8.module.ormbuilder.orm;

/**
 * This is a set of static methods used by the builder to simplify creation of the orm
 * mapping. It creates the nodes of the orm.xml file using the shema
 *
 * http://openjpa.apache.org/builds/2.2.1/apache-openjpa/docs/jpa_overview_meta_xml.html
 *
 *
 *
 * @author Michael Fortin
 */
public final class ORM {


    public static enum Strategy {
        TABLE, SEQUENCE,IDENTITY,AUTO
    }

    /**
     * Initialize the orm builder with the root entity node.
     *
     * @param className the full class name of the entity to implement.
     * @param tableName the database table name it maps too.
     * @return an entity node.
     */
    public static EntityNodeBuilder entity(String className, String tableName) {
        return new EntityNodeBuilder(className, tableName);
    }

    /**
     * Add column node to a basic,id or version column default values.
     *
     * @param name the name of the database column.
     * @return the column node.
     */
    public static Column column(String name) {
        return new Column(name);
    }

    /**
     * Add a column with all possible parameters.
     *
     * @param name the column name
     * @param length the column length
     * @param nullable is the column nullable or not.
     * @param unique is the column unique.
     * @return a column node to add to a basic,id, or version node.
     */
    public static Column column(String name, int length, boolean nullable, boolean unique) {
        return new Column(name,length,nullable,unique);
    }


    public static JoinColumn joinColumn(String name, String ref, String table) {
        return new JoinColumn(name,ref,table);
    }

    public static JoinColumn joinColumn(String name, String ref, boolean unique, String table) {
        return new JoinColumn(name,ref,unique,table);
    }

    /**
     * Added to an sequence generator node.
     *
     * @param strategy the strategy used to create a sequence.
     * @param generator the generator.
     * @return generated value
     */
    public static GeneratedValue generatedValue(Strategy strategy, String generator) {
        return new GeneratedValue(strategy,generator);
    }

    /**
     * typically added to an ID column of the entity to add a sequence generator.
     *
     * @param name the name of the generator
     * @param sequenceName the name of the sequence.
     * @return the sequence generator.
     */
    public static SequenceGenerator sequenceGenerator(String name, String sequenceName ) {
        return new SequenceGenerator(name,sequenceName);
    }

    public static SequenceGenerator sequenceGenerator(String name, String sequenceName, int init, int allocate) {
        return new SequenceGenerator(name,sequenceName,init,allocate);
    }
}
