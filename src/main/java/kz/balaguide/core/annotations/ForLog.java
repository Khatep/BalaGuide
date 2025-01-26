package kz.balaguide.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The {@code ForLog} annotation is intended for marking methods that should be
 * logged. This annotation can be used to indicate that a method requires
 * additional logging, through the use of Aspect-Oriented Programming.
 *
 * <p>The {@code ForLog} annotation is applied at the method level and will be
 * accessible at runtime.
 *
 * @Retention(RetentionPolicy.RUNTIME) allows the annotation to be accessible during runtime.
 * @Target(ElementType.METHOD) specifies that the annotation can only be applied to methods.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ForLog {
}
