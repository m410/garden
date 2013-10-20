package us.m410.j8.sample;


import us.m410.j8.persistence.orm.EntityFactory;

/**
 */
public interface MyService extends EntityFactory {
    public String get(String id);
}