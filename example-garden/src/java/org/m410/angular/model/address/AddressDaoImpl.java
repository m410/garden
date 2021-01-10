package org.m410.angular.model.address;

import org.m410.garden.module.jpa.JpaThreadLocal;
import org.m410.garden.module.jpa.impl.AbstractDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author m410
 */
public class AddressDaoImpl extends AbstractDao<Address,Long> implements AddressDao {
    private static final Logger log = LoggerFactory.getLogger(AddressDaoImpl.class);

    public AddressDaoImpl() {
        super(Address.class);
    }

    @Override
    public List<Address> listByPersonId(Long id) {
        return JpaThreadLocal.get()
                .createNamedQuery("findByPersonId") // , Address.class
                .setParameter("personId", id)
                .getResultList();
    }
}
