package ex1;
import java.sql.*;
import java.util.Set;

public class JDBCAddressDaoImpl extends CoreJDBCDao implements AddressDAO{
    @Override
    public Set<Address> findAll() {
        return null;
    }
    @Override
    public Address findExistingAddress(Address address) {
        String findByCityStreetCountrySQL = "SELECT * FROM Address WHERE city = ? AND street = ? AND country = ?";
        try (PreparedStatement findByCityStreetCountry = connection.prepareStatement(findByCityStreetCountrySQL)) {
            findByCityStreetCountry.setString(1, address.getCity());
            findByCityStreetCountry.setString(2, address.getStreet());
            findByCityStreetCountry.setString(3, address.getCountry());

            ResultSet rs = findByCityStreetCountry.executeQuery();
            if (rs.next()) {
                int existingAddressId = rs.getInt("id");
                return new Address(existingAddressId, address.getCity(), address.getStreet(), address.getCountry());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Address insert(Address address) {
        String insertAddressSQL = "INSERT INTO Address (city, street, country) VALUES (?, ?, ?)";
        try (PreparedStatement insertAddress = connection.prepareStatement(insertAddressSQL, Statement.RETURN_GENERATED_KEYS)) {
            insertAddress.setString(1, address.getCity());
            insertAddress.setString(2, address.getStreet());
            insertAddress.setString(3, address.getCountry());
            insertAddress.executeUpdate();

            ResultSet generatedKeys = insertAddress.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedAddressId = generatedKeys.getInt(1);
                address.setId(generatedAddressId);
                return address;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void update(Address address) {

    }

    @Override
    public void delete(int addressId) {

    }
}
