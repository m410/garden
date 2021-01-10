package org.m410.garden.module.ormbuilder.orm.fixtures;

import org.m410.garden.module.jpa.impl.PessimisticPrimaryKey;
import org.m410.garden.module.ormbuilder.orm.Entity;
import org.m410.garden.module.ormbuilder.orm.EntityFactory;

import java.util.Date;

import static org.m410.garden.module.ormbuilder.orm.ORM.*;

/**
 * @author m410
 */
public class Person extends PessimisticPrimaryKey<Long> implements EntityFactory {

    private String firstName;
    private String lastName;
    private Date createdOn;
    private Company company;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public Entity makeEntity() {
        return entity(getClass(),"person")
                .id("id", column("id").notNull(),
                        generatedValue(Strategy.SEQUENCE, "id_gen"),
                        sequenceGenerator("id_gen", "person_seq",1,1))
                .basic("firstName",column("first_name").length(36).notNull())
                .basic("lastName",column("last_name").length(36).notNull())
                .basic("createdOn",column("created_on").notNull())
                .oneToMany("company", Company.class,
                        joinTable("person_company",
                            joinColumn("company_id").notNull(),
                            inverseJoinColumn("person_id").notNull()))
                .make();
    }
}
