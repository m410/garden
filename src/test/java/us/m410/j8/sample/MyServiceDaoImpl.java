package us.m410.j8.sample;

import us.m410.j8.persistence.orm.Entity;

import static us.m410.j8.persistence.orm.ORM.*;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class MyServiceDaoImpl implements MyServiceDao {

    @Override
    public Entity makeEntity() {
        return entity("us.m410.demo.Person", "person").make();
    }
}
