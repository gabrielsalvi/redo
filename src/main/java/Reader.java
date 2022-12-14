import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Reader {

    private final File file;
    private final List<String> lines;
    private final List<Transaction> transactions;
    private final List<Transaction> transactionsToRedo;

    private void parse() {
        for (String line : lines) {
            if (line.startsWith("start")) {
                String name = line.replace("start ", "");
                transactions.add(new Transaction(name));
            } else if (line.startsWith("commit")) {
                String name = line.replace("commit ", "");
                Transaction transaction = findTransactionByName(name);
                if (transaction != null) transaction.commit();
                transactionsToRedo.add(transaction);
            } else if (line.startsWith("T")) {
                List<String> splittedLine = Arrays.stream(line.split(",")).collect(Collectors.toList());
                Transaction transaction = findTransactionByName(splittedLine.get(0));
                splittedLine.remove(0);
                if (transaction != null) transaction.addOperation(splittedLine);
            } else if (line.startsWith("CKPT")) {
                transactionsToRedo.clear();
            }
        }
    }

    public Reader(final File file) {
        this.file = file;
        this.lines = cleanLines(read());
        this.transactions = new ArrayList<>();
        this.transactionsToRedo = new ArrayList<>();
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

    public List<Transaction> getTransactionsToRedo() {
        return transactionsToRedo;
    }
}
