package kz.balaguide.common_module.core.exceptions.buisnesslogic.generic;

/**
 * Exception thrown to indicate that a child is ineligible for a particular action or enrollment.
 *
 * <p>This exception is a {@link RuntimeException} and can be used in scenarios
 * where an attempt is made to enroll or register a child who does not meet the eligibility criteria.
 */
public class IneligibleChildException extends RuntimeException {

    /**
     * Constructs a new {@code IneligibleChildException} with the specified detail message.
     *
     * @param message the detail message providing more information about the exception
     */
    public IneligibleChildException(String message) {
        super(message);
    }
}
