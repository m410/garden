package org.m410.garden.module.ormbuilder.orm.fixtures;

import org.m410.garden.module.jpa.impl.PessimisticPrimaryKey;
import org.m410.garden.module.ormbuilder.orm.Entity;
import org.m410.garden.module.ormbuilder.orm.EntityFactory;

import java.util.Date;

import static org.m410.garden.module.ormbuilder.orm.ORM.*;


/**
 * @author m410
 */
public class Account extends PessimisticPrimaryKey<Long> implements EntityFactory {

    private Date createdOn;
    private Company company;

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
        return entity(getClass(), "account")
                .id("id", column("id"), generatedValue(Strategy.SEQUENCE, "account_gen"),
                        sequenceGenerator("account_gen", "account_seq", 1, 1))
                .basic("created_on")
                .oneToOne("company", Company.class, joinColumn("company_id"))
                .make();
    }
}
