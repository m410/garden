package org.m410.j8.sample;


import org.m410.j8.persistence.orm.Entity;
import static org.m410.j8.persistence.orm.ORM.*;

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
        return entity("us.m410.j8.sample.Person","person").make();
    }

    @Override
    public String toString() {
        return "MyServiceImpl";
    }
}
