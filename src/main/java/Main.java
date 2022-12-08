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
        reader.getTransactionsToRedo()
              .forEach(t -> System.out.printf("A transação %s irá sofrer redo!\n", t.getName()));

        redo(reader.getTransactionsToRedo());
        databaseOperations.selectAll();
    }

    private static void redo(List<Transaction> transactions) {
        DatabaseOperations db = new DatabaseOperations();

        transactions.forEach(t -> t.getOperations().forEach(op -> {
            int currentValue = db.select(op.getTupleId(), op.getColumn());
            if (currentValue != op.getNewValue()) {
                db.update(op.getTupleId(), op.getColumn(), op.getNewValue());
                System.out.printf("%s alterou o valor da coluna %s de %s para %s na tupla %s\n",
                    t.getName(), op.getColumn(), op.getOldValue(), op.getNewValue(), op.getTupleId()
                );
            }
        }));
    }

}

