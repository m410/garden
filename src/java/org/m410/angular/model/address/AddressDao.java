package org.m410.angular.model.address;

import org.m410.garden.module.jpa.impl.Dao;

import java.util.List;

/**
 * @author m410
 */
public interface AddressDao extends Dao<Address,Long> {
    List<Address> listByPersonId(Long id);
}
