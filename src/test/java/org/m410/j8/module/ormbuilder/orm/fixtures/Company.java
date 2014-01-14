package org.m410.j8.module.ormbuilder.orm.fixtures;

import org.m410.j8.module.jpa.impl.OptimisticPrimaryKey;
import org.m410.j8.module.ormbuilder.orm.Entity;
import org.m410.j8.module.ormbuilder.orm.EntityFactory;

import java.util.Collection;
import java.util.Set;

import static org.m410.j8.module.ormbuilder.orm.ORM.*;

/**
 * @author m410
 */
public class Company extends OptimisticPrimaryKey<Long> implements EntityFactory {

    private String name;
    private Set<Person> employees;
    private Account account;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Person> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Person> employees) {
        this.employees = employees;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public Entity makeEntity() {
        return entity(getClass(),"company")
                .id("id", column("id"), generatedValue(Strategy.SEQUENCE, "company_gen"),
                        sequenceGenerator("company_gen", "company_seq",1,1))
                .version("version", column("version"))
                .basic("name",column("name").length(72))
                .manyToOne("employees",Person.class,"company")
                .oneToOne("account",Account.class,"company")
                .make();
    }
}
