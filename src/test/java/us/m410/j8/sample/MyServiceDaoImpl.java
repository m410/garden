package us.m410.j8.sample;

import us.m410.j8.persistence.orm.OrmBuilder;

import static us.m410.j8.persistence.orm.ORM.*;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class MyServiceDaoImpl implements MyServiceDao {
    @Override
    public OrmBuilder getOrmBuilder() {
        return entity("us.m410.j8.sample.Person", "table");
    }
}
