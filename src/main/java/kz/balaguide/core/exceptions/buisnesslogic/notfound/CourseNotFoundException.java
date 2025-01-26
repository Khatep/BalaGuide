package kz.balaguide.core.exceptions.buisnesslogic.notfound;

/**
 * Exception thrown when a specified course entity is not found in the system.
 * <p> This exception is typically used to indicate that an operation has failed
 * because the referenced course entity does not exist in the database
 * or another data source. </p>
 */
public class CourseNotFoundException extends RuntimeException {
    /**
     * Constructs a new {@code CourseNotFoundException} with the specified detail message.
     *
     * @param message the detail message, which provides more information about the error
     */
    public CourseNotFoundException(String message) {
        super(message);
    }
}
