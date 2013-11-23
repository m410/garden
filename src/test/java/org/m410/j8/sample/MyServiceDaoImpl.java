package org.m410.j8.sample;

import org.m410.j8.module.ormbuiler.orm.Entity;

import static org.m410.j8.module.ormbuiler.orm.ORM.*;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class MyServiceDaoImpl implements MyServiceDao {

    @Override
    public Entity makeEntity() {
        return entity("org.m410.demo.Person", "person").make();
    }
}
