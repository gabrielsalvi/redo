import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    public Connection connect() {
        Connection conn = null;
        DatabaseCredentials dbc = new DatabaseCredentials();

        try {
            conn = DriverManager.getConnection(dbc.getUrl(), dbc.getUser(), dbc.getPassword());
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }
}