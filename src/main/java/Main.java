import java.io.File;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Reader reader = new Reader(new File("input/entradaLog.txt"));
        List<String> lines = reader.read();
        lines.forEach(System.out::println);

        Database db = new Database();

        db.connect();
    }

}

