package kz.balaguide.core.exceptions.buisnesslogic.generic;

/**
 * Exception thrown to indicate that a course is full and cannot accept more enrollments.
 *
 * <p>This exception is a {@link RuntimeException} and can be used in situations
 * where an attempt is made to enroll a student in a course that has reached its capacity.
 */
public class CourseFullException extends RuntimeException {

    /**
     * Constructs a new {@code CourseFullException} with the specified detail message.
     *
     * @param message the detail message providing more information about the exception
     */
    public CourseFullException(String message) {
        super(message);
    }
}

