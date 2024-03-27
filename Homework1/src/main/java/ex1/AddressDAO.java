package ex1;

import java.util.Set;

public interface AddressDAO {
    Set<Address> findAll();

    Address findExistingAddress(Address address);

    Address insert(Address address);
    void update(Address address);
    void delete(int addressId);
}



