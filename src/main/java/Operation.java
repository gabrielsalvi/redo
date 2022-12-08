public class Operation {

    private final int tupleId;
    private final String column;
    private final int oldValue;
    private final int newValue;

    public Operation(final int tupleId, final String column, final int oldValue, final int newValue) {
        this.tupleId = tupleId;
        this.column = column;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "tupleId=" + tupleId +
                ", column='" + column + '\'' +
                ", oldValue=" + oldValue +
                ", newValue=" + newValue +
                '}';
    }
}
