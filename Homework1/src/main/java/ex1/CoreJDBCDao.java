package ex1;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CoreJDBCDao {
    protected Connection connection;
    CoreJDBCDao(){
        try {
            String url = "jdbc:mariadb://localhost:3306/hwone";
            String user = "root";
            String password = "root";
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.err.println("Connection cannot be established");
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println("Connection cannot be closed");
        }
    }
}
