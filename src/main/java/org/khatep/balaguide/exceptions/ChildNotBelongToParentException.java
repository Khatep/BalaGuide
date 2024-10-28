package org.khatep.balaguide.exceptions;

/**
 * Exception thrown to indicate that a specified child entity does not belong to
 * the expected parent entity.
 *
 * <p>This exception is a {@link RuntimeException} and can be used in cases
 * where there is a violation of the parent-child relationship in the application.
 */
public class ChildNotBelongToParentException extends RuntimeException {

    /**
     * Constructs a new {@code ChildNotBelongToParentException} with the specified detail message.
     *
     * @param message the detail message providing more information about the exception
     */
    public ChildNotBelongToParentException(String message) {
        super(message);
    }
}
