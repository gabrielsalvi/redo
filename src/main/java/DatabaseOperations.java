import java.sql.*;
import java.util.List;

public class DatabaseOperations implements AutoCloseable {

    private final Database database;
    private final Connection connection;

    public DatabaseOperations() {
        this.database = new Database();
        this.connection = database.getConnection();
    }

    public void createTable() {
        try {
            dropTable();
            PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS dados " +
                    "(id SERIAL PRIMARY KEY, a INT NOT NULL, b INT NOT NULL);");
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao executar o SQL:\n" + ex.getMessage());
        }
    }

    public boolean insertData(List<Integer> a, List<Integer> b) {
        boolean success = false;

        for (int i = 0; i < a.size(); i++) {
            Row row = new Row(a.get(i), b.get(i));

            try {
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO dados (a, b) VALUES (?, ?);");

                stmt.setInt(1, row.getA());
                stmt.setInt(2, row.getB());

                stmt.executeUpdate();
                success = true;
            } catch (SQLException e) {
                e.printStackTrace();
                success = false;
            }
        }

        return success;
    }

    private void dropTable() {
        try {
            PreparedStatement stmt = connection.prepareStatement("DROP TABLE IF EXISTS dados;");
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao executar o SQL:\n" + ex.getMessage());
        }
    }

    public int select(int id, String column) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM dados WHERE id = ?");
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(column);

        } catch (SQLException e) {
            System.out.println("Erro ao executar o SQL:\n" + e.getMessage());
            return -1;
        }

    }

    @Override
    public void close() {
        database.close();
    }

}
