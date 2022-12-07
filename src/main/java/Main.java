import java.io.File;
import java.util.ArrayList;
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
        List<String> lines = reader.getLines();

        List<String> startedThreads = getStartedThreads(lines);
        List<String> committedThreads = getCommittedThreads(lines);

        startedThreads.forEach(System.out::println);
        committedThreads.forEach(System.out::println);

    }

    public static List<String> getStartedThreads(List<String> lines) {
        return lines.stream()
                .filter(line -> line.startsWith("start"))
                .map(line -> line.replace("start ", ""))
                .collect(Collectors.toList());

    }

    public static List<String> getCommittedThreads(List<String> lines) {
        return lines.stream()
                .filter(line -> line.startsWith("commit"))
                .map(line -> line.replace("commit ", ""))
                .collect(Collectors.toList());

    }

}

