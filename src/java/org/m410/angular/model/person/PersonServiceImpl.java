package org.m410.angular.model.person;

import org.m410.garden.module.auth.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Example service with injected dao.
 * @author Michael Fortin
 */
public class PersonServiceImpl implements PersonService {
    private PersonDao personDao;

    @Override
    public Optional<User> authorize(Map<String,String> params) {
        String username = params.get("userName");
        String password = params.get("password");
        Optional<Person> person = personDao.findByUserPass(username, password);

        return person.flatMap(p->{
            String[] roles = p.getRoles().toArray(new String[p.getRoles().size()]);
            return Optional.of(new User(p.getUserName(),roles));
        });
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
