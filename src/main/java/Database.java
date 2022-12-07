import java.sql.*;

public class Database {

    private final Connection connection;

    public Database() {
        this.connection = Database.connect();
    }

    public static Connection connect() {
        DatabaseCredentials dbc = new DatabaseCredentials();

        try {
            return DriverManager.getConnection(dbc.getUrl(), dbc.getUser(), dbc.getPassword());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void close() {
        try {
            connection.close();
            System.out.println("closed");
        } catch (SQLException ex) {
            System.out.println("Erro ao encerrar a conexão com o banco de dados!\n" + ex.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}