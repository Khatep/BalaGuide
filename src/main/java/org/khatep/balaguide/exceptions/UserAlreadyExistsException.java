package org.khatep.balaguide.exceptions;

/**
 * Exception thrown when an attempt is made to create a user that already exists in the system.
 * <p> This exception is typically used to indicate that a new user cannot be created
 * because a user with the same identifying attribute(s), such as email or phone number,
 * already exists in the database. </p>
 */
public class UserAlreadyExistsException extends RuntimeException {
    /**
     * Constructs a new {@code UserAlreadyExistsException} with the specified detail message.
     *
     * @param message the detail message, providing information about the cause of the exception
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}

