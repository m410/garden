package org.m410.j8.sample;


import org.m410.j8.module.ormbuilder.orm.EntityFactory;

/**
 */
public interface MyService extends EntityFactory {
    public String get(String id);
}