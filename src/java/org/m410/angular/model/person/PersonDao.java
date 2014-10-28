package org.m410.angular.model.person;

import org.m410.garden.module.jpa.impl.Dao;

import java.util.Optional;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public interface PersonDao extends PersonEntityFactory, Dao<Person,Long> {

    Optional<Person> findByUserPass(String user,String pass);

    Person findBy(String userName);
}
