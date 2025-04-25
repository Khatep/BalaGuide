package kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound;

import jakarta.persistence.EntityNotFoundException;

public class TeacherNotFoundException extends EntityNotFoundException {
    public TeacherNotFoundException(String message) {
        super(message);
    }
}
