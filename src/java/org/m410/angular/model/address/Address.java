package org.m410.angular.model.address;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.cfg.ConstraintMapping;
import org.hibernate.validator.cfg.defs.NotNullDef;
import org.hibernate.validator.cfg.defs.SizeDef;
import org.m410.angular.model.person.Person;
import org.m410.garden.module.jpa.impl.OptimisticPrimaryKey;
import org.m410.garden.module.ormbuilder.orm.Entity;
import org.m410.garden.module.ormbuilder.orm.EntityFactory;

import javax.validation.Validation;
import javax.validation.Validator;

import java.lang.annotation.ElementType;

import static org.m410.garden.module.ormbuilder.orm.ORM.*;

/**
 * @author m410
 */
public class Address extends OptimisticPrimaryKey<Long> implements EntityFactory {

    private Person person;
    private String street;
    private String street2;
    private String city;
    private String state;
    private String postalCode;

    @Override public Long getId() { return super.getId(); }

    @Override public Integer getVersion() { return super.getVersion(); }

    public Person getPerson() { return person; }
    public void setPerson(Person person) { this.person = person; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getStreet2() { return street2; }
    public void setStreet2(String street2) { this.street2 = street2; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(street)
                .append(street2)
                .append(city)
                .append(state)
                .append(postalCode)
                .toString();
    }

    private static final String FIND_BY_PERSON_QUERY = "select a " +
            "from Address a " +
            "where a.person in (" +
            "   select p " +
            "   from Person p " +
            "   where p.id=:personId)";

    @Override
    public Entity makeEntity() {
        return entity(getClass(),"address")
                .namedQuery("findByPersonId", FIND_BY_PERSON_QUERY)
                .id("id", column("id"), generatedValue(Strategy.SEQUENCE, "address_gen"),
                        sequenceGenerator("address_gen", "address_seq",1,1))
                .version("version", column("version"))
                .basic("street",column("street").length(36).notNull())
                .basic("street2",column("street_2").length(36).notNull())
                .basic("city",column("city").length(72).notNull())
                .basic("state",column("state").length(2).notNull())
                .basic("postalCode",column("postal_code").length(10).notNull())
                .manyToOne("person",Person.class, joinColumn("person_id"))
                .make();
    }

    public Validator validator() {
        HibernateValidatorConfiguration configuration = Validation
                .byProvider(HibernateValidator.class)
                .configure();
        ConstraintMapping constraintMapping = configuration.createConstraintMapping();

        constraintMapping.type(Address.class)
                .property("street", ElementType.FIELD).constraint(new NotNullDef())
                .property("city", ElementType.FIELD).constraint(new NotNullDef())
                .constraint(new SizeDef().min(2).max(14));


        return configuration.addMapping(constraintMapping)
                .buildValidatorFactory()
                .getValidator();
    }
}
