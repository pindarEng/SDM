package ex1;

public interface CreditCardDao {
    CreditCard findExistingCreditCard(CreditCard creditCard);
    CreditCard insert(CreditCard creditCard);
}