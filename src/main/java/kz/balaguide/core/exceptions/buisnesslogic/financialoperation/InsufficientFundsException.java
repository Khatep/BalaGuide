package kz.balaguide.core.exceptions.buisnesslogic.financialoperation;

/**
 * Exception thrown to indicate that an account has insufficient funds to complete an operation.
 *
 * <p>This exception is a {@link RuntimeException} and can be used in scenarios
 * where an attempt is made to withdraw or transfer funds exceeding the account balance.
 */
public class InsufficientFundsException extends FinancialOperationException {

    /**
     * Constructs a new {@code InsufficientFundsException} with the specified detail message.
     *
     * @param message the detail message providing more information about the exception
     */
    public InsufficientFundsException(String message) {
        super(message);
    }
}
