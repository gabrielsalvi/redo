import java.sql.*;

public class Database {

    public static Connection connection;
    public static Statement stmt;
    public static ResultSet rs;

    public static Connection connect() {
        connection = null;
        DatabaseCredentials dbc = new DatabaseCredentials();

        try {
            connection = DriverManager.getConnection(dbc.getUrl(), dbc.getUser(), dbc.getPassword());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return connection;
    }

    public static void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao encerrar a conexão com o banco de dados!\n" + ex.getMessage());
        }
    }

    public static void execute(String sql) {
        try {
            Database.connect();
            stmt = connection.createStatement(rs.TYPE_SCROLL_INSENSITIVE, rs.CONCUR_READ_ONLY);
            stmt.execute(sql);
            Database.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao executar o SQL:\n" + ex.getMessage());
        }
    }

}