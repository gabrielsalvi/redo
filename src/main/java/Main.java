import java.io.File;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Parser parser = new Parser("input/metadado.json");

        List<Integer> firstColumnValues = parser.getFirstColumnValues();
        List<Integer> secondColumnValues = parser.getSecondColumnValues();

        DatabaseOperations databaseOperations = new DatabaseOperations();

        databaseOperations.createTable();
        databaseOperations.insertData(firstColumnValues, secondColumnValues);

        Reader reader = new Reader(new File("input/entradaLog.txt"));

        reader.getTransactions()
                .stream()
                .filter(Transaction::isCommitted)
                .forEach(t -> System.out.printf("A transação %s irá sofrer redo!\n", t.getName()));

    }

}

