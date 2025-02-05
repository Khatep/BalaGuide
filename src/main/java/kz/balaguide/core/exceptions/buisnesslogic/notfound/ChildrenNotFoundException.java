package kz.balaguide.core.exceptions.buisnesslogic.notfound;


import jakarta.persistence.EntityNotFoundException;
public class ChildrenNotFoundException extends EntityNotFoundException {
    public ChildrenNotFoundException(String message) {
        super(message);
    }
}
