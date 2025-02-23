package kz.balaguide.common_module.core.exceptions.technical;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
