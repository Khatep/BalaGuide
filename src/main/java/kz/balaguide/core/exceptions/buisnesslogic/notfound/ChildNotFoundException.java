package kz.balaguide.core.exceptions.buisnesslogic.notfound;

import kz.balaguide.core.exceptions.buisnesslogic.generic.EntityNotFoundException;

/**
 * Exception thrown when a specified child entity is not found in the system.
 * <p> This exception is typically used to signal that an operation failed
 * because the referenced child entity does not exist in the database</p>
 */
public class ChildNotFoundException extends EntityNotFoundException {
    /**
     * Constructs a new {@code ChildNotFoundException} with the specified detail message.
     *
     * @param message the detail message, which provides more information about the error
     */
    public ChildNotFoundException(String message) {
        super(message);
    }
}
