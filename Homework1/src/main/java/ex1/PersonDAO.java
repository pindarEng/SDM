package ex1;

import java.util.Set;

public interface PersonDAO {
    Set<Person> findAll();
    boolean doesPersonExist(Person person);
    Person insert(Person person);
    void update(Person person);
    void delete(int personId);

}

