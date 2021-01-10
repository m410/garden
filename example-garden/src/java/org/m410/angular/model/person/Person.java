package org.m410.angular.model.person;

import org.apache.commons.lang3.builder.ToStringBuilder;

import org.m410.angular.model.address.Address;
import org.m410.garden.module.jpa.impl.Id;
import org.m410.garden.module.ormbuilder.orm.Entity;
import org.m410.garden.module.ormbuilder.orm.EntityFactory;

import java.util.List;
import java.util.Set;

import static org.m410.garden.module.ormbuilder.orm.ORM.*;


/**
 * A basic entity class.
 *
 * @author Michael Fortin
 */
public class Person implements Id<Long> {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    private String userName;
    private String password;
    private List<String> roles;

    private Set<Address> addresses;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Set<Address> getAddresses() { return addresses; }
    public void setAddresses(Set<Address> addresses) { this.addresses = addresses; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id",getId())
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("email", email)
                .toString();
    }
}

/**
 * adding the interface here below the entity is admittedly not a common java
 * practice, but it's here to keep the configuration relative to the actual
 * entity class it maps.  It could be part of the entity, or in the dao, just as
 * long as it's able to be wired into the Application class.
 */
interface PersonEntityFactory extends EntityFactory {
    static final String LOGIN = "select p from Person p where p.userName=:user and p.password=:pass";
    static final String FIND_BY_USER = "select p from Person p where p.userName=:user";

    @Override
    default Entity makeEntity() {
        return entity(Person.class, "person")
                .namedQuery("findByUserPass", LOGIN)
                .namedQuery("findByUser", FIND_BY_USER)
                .id("id", column("id"),
                        generatedValue(Strategy.SEQUENCE, "id_gen"),
                        sequenceGenerator("id_gen", "person_seq", 1, 1))
                .basic("firstName", column("first_name").length(36).notNull())
                .basic("lastName", column("last_name").length(36).notNull())
                .basic("userName", column("user_name").length(36).notNull().unique())
                .basic("password", column("password").length(36).notNull())
                .basic("email", column("email").length(124).notNull())
                .elementCollection("roles", String.class,
                        column("role_name").length(36).notNull(),
                        collectionTable("person_roles", joinColumn("person_id")))
                .oneToMany("addresses", Address.class, "person")
                .make();
    }
}
