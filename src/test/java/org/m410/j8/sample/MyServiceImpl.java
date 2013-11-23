package org.m410.j8.sample;


import org.m410.j8.module.ormbuilder.orm.Entity;
import static org.m410.j8.module.ormbuilder.orm.ORM.*;

/**
 */
public class MyServiceImpl implements MyService {

    private MyServiceDao myServiceDao;

    public MyServiceImpl(MyServiceDao myServiceDao) {
        this.myServiceDao = myServiceDao;
    }

    public String get(String id) {
        return "got id: " + id;
    }

    @Override
    public Entity makeEntity() {
        return entity("org.m410.j8.sample.Person","person").make();
    }

    @Override
    public String toString() {
        return "MyServiceImpl";
    }
}
