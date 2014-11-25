package org.m410.garden.fixtures;

import org.m410.garden.module.ormbuilder.orm.Entity;

import static org.m410.garden.module.ormbuilder.orm.ORM.*;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class MyServiceDaoImpl implements MyServiceDao {

    @Override
    public Entity makeEntity() {
        return entity(FixturePerson.class, "person").make();
    }
}
