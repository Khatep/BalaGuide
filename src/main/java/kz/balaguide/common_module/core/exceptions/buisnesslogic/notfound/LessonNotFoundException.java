package kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound;

public class LessonNotFoundException extends RuntimeException {
    public LessonNotFoundException(String message) {
        super(message);
    }
}
