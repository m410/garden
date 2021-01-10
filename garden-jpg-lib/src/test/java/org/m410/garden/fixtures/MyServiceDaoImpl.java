package org.m410.garden.fixtures;

import org.m410.garden.module.ormbuilder.orm.Entity;

import static org.m410.garden.module.ormbuilder.orm.ORM.entity;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public final class MyServiceDaoImpl implements MyServiceDao {

    @Override
    public Entity makeEntity() {
        return entity(FixturePerson.class, "person").make();
    }
}
