package ex1;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCCreditCardDaoImpl extends CoreJDBCDao implements CreditCardDao {
    @Override
    public CreditCard findExistingCreditCard(CreditCard creditCard) {
        String findByIbanSQL = "SELECT * FROM CreditCard WHERE iban = ?";
        try (PreparedStatement findByIban = connection.prepareStatement(findByIbanSQL)) {
            findByIban.setString(1, creditCard.getIban());
            ResultSet rs = findByIban.executeQuery();
            if (rs.next()) {
                int existingCreditCardId = rs.getInt("id");
                return new CreditCard(existingCreditCardId, creditCard.getIban(), creditCard.getAmount());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CreditCard insert(CreditCard creditCard) {
        String insertCreditCardSQL = "INSERT INTO CreditCard (iban, amount, person_id) VALUES (?, ?, ?)";
        try (PreparedStatement insertCreditCard = connection.prepareStatement(insertCreditCardSQL, Statement.RETURN_GENERATED_KEYS)) {
            insertCreditCard.setString(1, creditCard.getIban());
            insertCreditCard.setInt(2, creditCard.getAmount());

            if (creditCard.getPerson() != null) {
                insertCreditCard.setInt(3, creditCard.getPerson().getId());
            } else {
                insertCreditCard.setNull(3, java.sql.Types.INTEGER);
            }

            insertCreditCard.executeUpdate();

            ResultSet generatedKeys = insertCreditCard.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedCreditCardId = generatedKeys.getInt(1);
                creditCard.setId(generatedCreditCardId);
                return creditCard;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}