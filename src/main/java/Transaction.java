import java.util.ArrayList;
import java.util.List;

public class Transaction {

    private final String name;
    private boolean isCommitted;
    private final List<Operation> operations;

    public Transaction(final String name) {
        this.name = name;
        this.operations = new ArrayList<>();
        this.isCommitted = false;
    }

    public void addOperation(final List<String> data) {
        Operation operation = new Operation(
                Integer.parseInt(data.get(0)),
                data.get(1),
                Integer.parseInt(data.get(2)),
                Integer.parseInt(data.get(3))
        );
        operations.add(operation);
    }

    public String getName() {
        return name;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void commit() {
        isCommitted = true;
    }

    public boolean isCommitted() {
        return isCommitted;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "name='" + name + '\'' +
                ", isCommitted=" + isCommitted +
                ", operations=" + operations +
                '}';
    }
}
