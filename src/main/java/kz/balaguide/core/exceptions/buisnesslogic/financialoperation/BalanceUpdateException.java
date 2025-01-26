package kz.balaguide.core.exceptions.buisnesslogic.financialoperation;

/**
 * Exception thrown when a failure occurs during the balance update process for an Education Center,
 * specifically after a payment transaction associated with a parent entity has been processed.
 * <p>
 * This exception is used to indicate that the balance update could not be completed and the
 * transaction has been rolled back.
 * </p>
 */
public class BalanceUpdateException extends RuntimeException {
    /**
     * Constructs a new BalanceUpdateException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception.
     */
    public BalanceUpdateException(String message) {
        super(message);
    }
}
