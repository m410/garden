package org.m410.angular.model.address;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author m410
 */
public interface AddressService {

    Set<ConstraintViolation<Address>> validate(Address address);

    Address save(Address address);

    List<Address> listByPersonId(Long id);

    Optional<Address> get(Long id);

    void delete(Long addressId);
}
