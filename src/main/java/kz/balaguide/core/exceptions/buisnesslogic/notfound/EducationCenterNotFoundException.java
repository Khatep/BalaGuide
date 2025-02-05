package kz.balaguide.core.exceptions.buisnesslogic.notfound;

import jakarta.persistence.EntityNotFoundException;

/**
 * Exception thrown when a specified education center entity is not found in the system.
 * <p> This exception is typically used to indicate that an operation failed
 * because the referenced education center entity does not exist in the database</p>
 */
public class EducationCenterNotFoundException extends EntityNotFoundException {
    /**
     * Constructs a new {@code EducationCenterNotFoundException} with the specified detail message.
     *
     * @param message the detail message, which provides more information about the error
     */
    public EducationCenterNotFoundException(String message) {
        super(message);
    }
}

