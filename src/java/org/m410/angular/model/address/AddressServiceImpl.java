package org.m410.angular.model.address;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author m410
 */
public class AddressServiceImpl implements AddressService {
    private ValidatorFactory f = Validation.buildDefaultValidatorFactory();
    private AddressDao addressDao;

    public AddressServiceImpl(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    @Override
    public Set<ConstraintViolation<Address>> validate(Address address) {
        return f.getValidator().validate(address);
    }

    public Address save(Address address) {
       return addressDao.insert(address);
    }

    @Override
    public List<Address> listByPersonId(Long id) {
        return addressDao.listByPersonId(id);
    }

    @Override
    public Optional<Address> get(Long id) {
        return addressDao.get(id);
    }

    @Override
    public void delete(Long addressId) {
        addressDao.delete(addressDao.get(addressId).get());
    }
}
