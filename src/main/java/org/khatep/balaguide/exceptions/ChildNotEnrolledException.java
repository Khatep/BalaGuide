package org.khatep.balaguide.exceptions;

/**
 * Exception thrown when an operation is attempted on a child who is not enrolled in any courses.
 * <p>
 * This exception is used to indicate that the specified child does not have any active course enrollments,
 * preventing the intended action.
 * </p>
 */
public class ChildNotEnrolledException extends RuntimeException {
    /**
     * Constructs a new ChildNotEnrolledException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception.
     */
    public ChildNotEnrolledException(String message) {
        super(message);
    }
}