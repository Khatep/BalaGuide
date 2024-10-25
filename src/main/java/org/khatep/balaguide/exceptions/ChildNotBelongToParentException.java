package org.khatep.balaguide.exceptions;

public class ChildNotBelongToParentException extends RuntimeException {
    public ChildNotBelongToParentException(String message) {
        super(message);
    }
}
