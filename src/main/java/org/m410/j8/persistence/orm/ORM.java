package org.m410.j8.persistence.orm;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public final class ORM {
    public static EntityNodeBuilder entity(String className, String tableName) {
        return new EntityNodeBuilder(className, tableName);
    }

    public static Column column(String name) {
        return new Column(name);
    }

    public static Column column(String name, int length, boolean nullable, boolean unique) {
        return new Column(name,length,nullable,unique);
    }

    public static JoinColumn joinColumn(String name, String ref, String table) {
        return new JoinColumn(name,ref,table);
    }

    public static JoinColumn joinColumn(String name, String ref, boolean unique, String table) {
        return new JoinColumn(name,ref,unique,table);
    }

    public static GeneratedValue generatedValue(String strategy, String generator) {
        return new GeneratedValue(strategy,generator);
    }

    public static SequenceGenerator sequenceGenerator(String name, String sequenceName ) {
        return new SequenceGenerator(name,sequenceName);
    }
}
