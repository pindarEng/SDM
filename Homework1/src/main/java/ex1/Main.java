package ex1;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


public class Main {
    public static void main(String[] args) {
        JDBCPersonDaoImpl personDao = new JDBCPersonDaoImpl();
        Set<Person> persons=personDao.findAll();
        persons.stream().forEach(System.out::println);

        Address address = new Address("uf1212221", "uf22312212", "uf32331");
        CreditCard creditCard1 = new CreditCard("100000001", 1000);
        CreditCard creditCard2 = new CreditCard("100000002", 10002);
        Set<CreditCard> creditCards = new HashSet<>();
        creditCards.add(creditCard1);
        creditCards.add(creditCard2);

        Person person = new Person("finallyMERGE233", LocalDate.of(123, 1, 2), "testing436", address, creditCards);
        Person insertedPerson = personDao.insert(person);

        if (insertedPerson != null) {
            System.out.println("\nPerson inserted successfully: \n" + insertedPerson);
        }
        // verficare ca sa vedem daca a bagat duplicat in db.

        personDao.closeConnection();

    }
}