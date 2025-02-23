package kz.balaguide.common_module.aop.pointcuts;

import org.aspectj.lang.annotation.Pointcut;

public class LoggingPointcuts {

    @Pointcut("execution(* kz.balaguide.controllers.*.*(..))")
    public final void loggingAllControllersPointcut() {}

    @Pointcut("@annotation(kz.balaguide.common_module.core.annotations.ForLog)")
    public final void loggingMethodsWhichAnnotatedByForLogAnnotationPointcut() {}

    @Pointcut("@annotation(org.springframework.web.bind.annotation.ExceptionHandler)")
    public final void loggingExceptionHandlers() {}
}
