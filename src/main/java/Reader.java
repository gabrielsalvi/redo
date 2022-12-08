import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Reader {

    private File file;
    private List<String> lines;
    private List<Transaction> transactions;

    private void parse() {
        for (String line : lines) {
            if (line.startsWith("start")) {
                String name = line.replace("start ", "");
                transactions.add(new Transaction(name));
            } else if (line.startsWith("commit")) {
                String name = line.replace("commit ", "");
                Transaction transaction = findTransactionByName(name);
                if (transaction != null) transaction.commit();
            } else if (line.startsWith("T")) {
                List<String> splittedLine = Arrays.stream(line.split(",")).collect(Collectors.toList());
                Transaction transaction = findTransactionByName(splittedLine.get(0));
                splittedLine.remove(0);
                if (transaction != null) transaction.addOperation(splittedLine);
            }
        }
    }

    public Reader(final File file) {
        this.file = file;
        this.lines = cleanLines(read());
        this.transactions = new ArrayList<>();
        parse();
    }

    private List<String> read() {
        List<String> lines = new ArrayList<>();

        try {
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) lines.add(input.nextLine());
            input.close();
        } catch (FileNotFoundException e) {
            System.out.printf("Não foi possível abrir o arquivo '%s'!\n", file.getName());
            System.exit(0);
        }

        return lines;
    }

    private List<String> cleanLines(List<String> lines) {
        return lines.stream()
                .map(line -> line.replace("<", "")
                    .replace(">", "")
                    .replace("(", "")
                    .replace(")",""))
                .collect(Collectors.toList());
    }

    private Transaction findTransactionByName(final String name) {
        for (Transaction transaction : transactions) {
            if (transaction.getName().equals(name)) return transaction;
        }

        return null;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
