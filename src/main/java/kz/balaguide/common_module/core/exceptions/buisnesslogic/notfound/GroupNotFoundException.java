package kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound;

import jakarta.persistence.EntityNotFoundException;

public class GroupNotFoundException extends EntityNotFoundException {
    public GroupNotFoundException(String message) {
        super(message);
    }
}
