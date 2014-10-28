package org.m410.angular.model.person;

import org.m410.garden.module.jpa.JpaThreadLocal;
import org.m410.garden.module.jpa.impl.AbstractDao;

import java.util.Optional;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class PersonDaoImpl extends AbstractDao<Person,Long> implements PersonDao {

    public PersonDaoImpl() {
        super(Person.class);
    }

    @Override
    public Person findBy(String userName) {
        return (Person)JpaThreadLocal.get()
                .createNamedQuery("findByUser")
                .setParameter("user",userName)
                .getSingleResult();
    }

    @Override @SuppressWarnings("unchecked")
    public Optional<Person> findByUserPass(String user, String pass) {
        return JpaThreadLocal.get()
                        .createNamedQuery("findByUserPass")
                        .setParameter("user",user)
                        .setParameter("pass",pass)
                        .getResultList().stream().findFirst();
    }
}
