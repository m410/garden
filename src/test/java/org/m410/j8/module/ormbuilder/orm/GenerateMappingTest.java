package org.m410.j8.module.ormbuilder.orm;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.m410.j8.module.ormbuilder.orm.fixtures.Account;
import org.m410.j8.module.ormbuilder.orm.fixtures.Company;
import org.m410.j8.module.ormbuilder.orm.fixtures.Person;

import static org.junit.Assert.*;

/**
 * @author m410
 */
@RunWith(JUnit4.class)
public class GenerateMappingTest {

    @Test
    public void createEntityMappings() throws Exception {
        Person person = new Person();
        Company company = new Company();
        Account account = new Account();

        String result = new OrmXmlBuilder()
                .addEntity(person.makeEntity())
                .addEntity(company.makeEntity())
                .addEntity(account.makeEntity())
                .make();

        assertNotNull(result);

    }
}
