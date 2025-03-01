package kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound;

import jakarta.persistence.EntityNotFoundException;

/**
 * Exception thrown when a specified parent entity is not found in the system.
 * <p> This exception is typically used to signal that an operation failed
 * because the referenced parent entity does not exist in the database
 * or another data source.</p>
 */
public class ParentNotFoundException extends EntityNotFoundException {
    /**
     * Constructs a new {@code ParentNotFoundException} with the specified detail message.
     *
     * @param message the detail message, which provides more information about the error
     */
    public ParentNotFoundException(String message) {
        super(message);
    }
}
