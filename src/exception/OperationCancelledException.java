package exception;

public class OperationCancelledException extends RuntimeException {
    public OperationCancelledException(String message) {
        super("Operation cancelled.");
    }
}