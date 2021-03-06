package org.m410.garden.fixtures;


import org.m410.garden.module.ormbuilder.orm.Entity;

import static org.m410.garden.module.ormbuilder.orm.ORM.entity;

/**
 */
public final class MyServiceImpl implements MyService {

    private MyServiceDao myServiceDao;

    public MyServiceImpl(MyServiceDao myServiceDao) {
        this.myServiceDao = myServiceDao;
    }

    public String get(String id) {
        return "got id: " + id;
    }

    @Override
    public Entity makeEntity() {
        return entity(FixturePerson.class,"person").make();
    }

    @Override
    public String toString() {
        return "MyServiceImpl";
    }
}
