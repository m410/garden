package us.m410.j8.sample;

import us.m410.j8.orm.OrmBuilder;
import us.m410.j8.orm.OrmGenerator;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class MyServiceDaoImpl implements MyServiceDao {
    @Override
    public OrmBuilder getOrmBuilder() {
        return new OrmBuilder("us.m410.j8.sample.Person").make();
    }
}
