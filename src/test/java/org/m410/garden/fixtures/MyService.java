package org.m410.garden.fixtures;


import org.m410.garden.module.ormbuilder.orm.EntityFactory;

/**
 */
public interface MyService extends EntityFactory {
    public String get(String id);
}