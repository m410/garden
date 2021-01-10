package org.m410.angular.model.person;

import org.m410.garden.controller.auth.User;
import org.m410.garden.controller.auth.AuthorizationStatus;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Example service with injected dao.
 * @author Michael Fortin
 */
public class PersonServiceImpl implements PersonService {
    private PersonDao personDao;


    public AuthorizationStatus<Person> authorize(Map<String,String> params) {
        String username = params.get("userName");
        String password = params.get("password");
        Optional<Person> optPerson = personDao.findByUserPass(username, password);
        // need optional constructor for status
        return AuthorizationStatus.option(optPerson, "Not Found");
    }

    public PersonServiceImpl(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public Person get(Long id) {
        return personDao.get(id).get();
    }

    @Override
    public Person save(Person person) {
        System.out.println("Saving:"+person);
        return personDao.insert(person);
    }

    @Override
    public List<Person> list() {
        return personDao.list();
    }

    @Override
    public void delete(Person person) {
        personDao.delete(person);
    }

    @Override
    public Person findBy(String userName) {
        return personDao.findBy(userName);
    }
}
