package exception;

public class OperationCancelledException extends Exception {
    public OperationCancelledException() {
        super("Operation cancelled.");
    }
}