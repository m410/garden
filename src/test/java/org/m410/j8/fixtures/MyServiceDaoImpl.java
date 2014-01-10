package org.m410.j8.fixtures;

import org.m410.j8.module.ormbuilder.orm.Entity;

import static org.m410.j8.module.ormbuilder.orm.ORM.*;

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
