package ex1;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

public class JDBCPersonDaoImpl extends CoreJDBCDao implements  PersonDAO {

    public boolean doesPersonExist(Person person) {
        String findPersonSQL = "SELECT COUNT(*) FROM Person WHERE name = ? AND birthDate = ? AND job = ?";
        try (PreparedStatement findPerson = connection.prepareStatement(findPersonSQL)) {
            findPerson.setString(1, person.getName());
            findPerson.setObject(2, person.getBirthDate());
            findPerson.setString(3, person.getJob());
            ResultSet resultSet = findPerson.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public Set<Person> findAll() {
        Set<Person> persons = new HashSet<>();
        String findAllPersonsSQL = "SELECT p.id, p.name, p.birthDate, p.job, a.id AS address_id, " +
                "a.city, a.street, a.country, c.id AS creditcard_id, c.iban, c.amount " +
                "FROM Person p " +
                "JOIN Address a ON p.address_id = a.id " +
                "LEFT JOIN CreditCard c ON p.id = c.person_id";
        try (PreparedStatement findAllPersons = connection.prepareStatement(findAllPersonsSQL)) {
            ResultSet rs = findAllPersons.executeQuery();
            Map<Integer, Person> personMap = new HashMap<>();
            while (rs.next()) {
                int personId = rs.getInt("id");
                String personName = rs.getString("name");
                LocalDate birthDate = rs.getDate("birthDate").toLocalDate();
                String job = rs.getString("job");

                int addressId = rs.getInt("address_id");
                String addressCity = rs.getString("city");
                String addressStreet = rs.getString("street");
                String addressCountry = rs.getString("country");
                Address personAddress = new Address(addressId, addressCity, addressStreet, addressCountry);

                int creditCardId = rs.getInt("creditcard_id");
                String iban = rs.getString("iban");
                int amount = rs.getInt("amount");

                personMap.putIfAbsent(personId, new Person(personName, birthDate, job, personAddress));

                Person person = personMap.get(personId);
                person.addCreditCard(new CreditCard(creditCardId, iban, amount));
            }
            persons.addAll(personMap.values());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persons;
    }

    @Override
    public Person insert(Person person) {
        if (doesPersonExist(person)) {
            System.out.println("Person already exists in the database.");
            return null;
        }
        JDBCAddressDaoImpl addressDao = new JDBCAddressDaoImpl();
        Address existingAddress = addressDao.findExistingAddress(person.getAddress());
        if (existingAddress != null) {
            person.setAddress(existingAddress);
        } else {
            Address insertedAddress = addressDao.insert(person.getAddress());
            if (insertedAddress != null) {
                person.setAddress(insertedAddress);
            } else {
                System.out.println("Failed to insert address into the database.");
                return null;
            }
        }

        String insertPersonSQL = "INSERT INTO Person (name, birthDate, job, address_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement insertPerson = connection.prepareStatement(insertPersonSQL, Statement.RETURN_GENERATED_KEYS)) {
            insertPerson.setString(1, person.getName());
            insertPerson.setObject(2, person.getBirthDate());
            insertPerson.setString(3, person.getJob());
            insertPerson.setInt(4, person.getAddress().getId());
            insertPerson.executeUpdate();

            ResultSet personKeys = insertPerson.getGeneratedKeys();
            int personId = -1;
            if (personKeys.next()) {
                personId = personKeys.getInt(1);
            }
            person.setId(personId);
            JDBCCreditCardDaoImpl creditCardDao = new JDBCCreditCardDaoImpl();
            for (CreditCard creditCard : person.getCreditCards()) {
                creditCard.setPerson(person);
                CreditCard insertedCreditCard = creditCardDao.insert(creditCard);
                if (insertedCreditCard != null) {
                    person.addCreditCard(insertedCreditCard);
                } else {
                    System.out.println("Failed to insert credit card into the database.");
                    return null;
                }
            }

            return person;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



    @Override
    public void update(Person person) {

    }

    @Override
    public void delete(int personId) {

    }
}
