package org.khatep.balaguide.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.khatep.balaguide.aop.annotations.ForLog;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    /**
     * Logs information before the execution of methods annotated with {@link ForLog}.
     *
     * @param joinPoint provides reflective access to both the state available at a join point and the static part of the join point.
     * @param param the parameter passed to the method; can be null.
     */
    @Before("@annotation(org.khatep.balaguide.aop.annotations.ForLog) && args(param,..)")
    public void logBefore(JoinPoint joinPoint, Object param) {
        log.info("Before method execution");
        log.info("Method: {}", joinPoint.getSignature().toString());
        log.info("Parameters: {}", param != null ? param.toString() : "No parameters");
    }

    /**
     * Logs execution time for methods annotated with {@link ForLog}.
     *
     * @param proceedingJoinPoint provides reflective access to both the state available at a join point and the static part of the join point.
     * @return the result of the method execution.
     * @throws Throwable if the method execution fails.
     */
    @Around("@annotation(org.khatep.balaguide.aop.annotations.ForLog)")
    public Object logAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        try {
            Object result = proceedingJoinPoint.proceed();
            return result;
        } finally {
            long endTime = System.currentTimeMillis();
            log.info("After method execution");
            log.info("Method: {}", proceedingJoinPoint.getSignature().toString());
            log.info("Execution time: {} ms", (endTime - startTime));
        }
    }

    /**
     * Logs information when a method annotated with {@link ForLog} throws an exception.
     *
     * @param joinPoint provides reflective access to both the state available at a join point and the static part of the join point.
     * @param exception the exception thrown by the method.
     */
    @AfterThrowing(pointcut = "@annotation(org.khatep.balaguide.aop.annotations.ForLog)", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        log.error("Method thrown an exception: {}", joinPoint.getSignature().toString());
        log.error("Exception message: {}", exception.getMessage());
    }
}
