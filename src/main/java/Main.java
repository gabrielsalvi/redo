import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

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


    public static List<String> getStartedTransactions(List<String> lines) {
        return lines.stream()
                .filter(line -> line.startsWith("start"))
                .map(line -> line.replace("start ", ""))
                .collect(Collectors.toList());

    }

    public static List<String> getCommittedTransactions(List<String> lines) {
        return lines.stream()
                .filter(line -> line.startsWith("commit"))
                .map(line -> line.replace("commit ", ""))
                .collect(Collectors.toList());

    }

}

