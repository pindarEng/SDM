package ex1;

public class CreditCard {
    private int id;
    private String iban;
    private int amount;

    private Person person;

    public CreditCard(String iban, int amount) {
        this.iban = iban;
        this.amount = amount;
    }

    public CreditCard(String iban, int amount, Person person) {
        this.iban = iban;
        this.amount = amount;
        this.person = person;
    }
    public CreditCard(int id, String iban, int amount) {
        this.id = id;
        this.iban = iban;
        this.amount = amount;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "id=" + id +
                ", iban='" + iban + '\'' +
                ", amount=" + amount +
                '}';
    }
}
