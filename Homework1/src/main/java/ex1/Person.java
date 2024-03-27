package ex1;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Person {
    private int id;
    private String name;
    private LocalDate birthDate;
    private String job;

    private Address address;
    private Set<CreditCard> creditCards;

    public Person(String name, LocalDate birthDate, String job, Address address, Set<CreditCard> creditCards) {
        this.name = name;
        this.birthDate = birthDate;
        this.job = job;
        this.address = address;
        if (creditCards != null) {
            this.creditCards = creditCards;
        } else {
            this.creditCards = new HashSet<>();
        }
    }

    public Person(String name, LocalDate birthDate, String job,Address address) {
        this.name = name;
        this.birthDate = birthDate;
        this.job = job;
        this.address = address;
        this.creditCards = new HashSet<>();
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", job='" + job + '\'' +
                ", address=" + address +
                ", creditCards=" + creditCards +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) && Objects.equals(birthDate, person.birthDate) && Objects.equals(job, person.job);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthDate, job);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
    public void addCreditCard(CreditCard creditCard) {
        creditCards.add(creditCard);
    }

    public void removeCreditCard(CreditCard creditCard) {
        creditCards.remove(creditCard);
    }

    public Set<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(Set<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
