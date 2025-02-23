package kz.balaguide.common_module.aop.aspects;

import jakarta.servlet.http.HttpServletRequest;
import kz.balaguide.auth_module.utils.GetUsernameFromSecurityContextHolder;
import kz.balaguide.common_module.core.annotations.ForLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class LoggingAspect {

    private final HttpServletRequest request;

    // Log request details before the method executes
    @Before("kz.balaguide.common_module.aop.pointcuts.LoggingPointcuts.loggingAllControllersPointcut()")
    public void logRequestDetails(JoinPoint joinPoint) {
        String ipAddress = request.getRemoteAddr();
        String requestUrl = request.getRequestURL().toString();
        String username = GetUsernameFromSecurityContextHolder.getUsername();

        log.info("REQUEST: Method = {}, URL = {}, IP Address = {}, Username = {}, Arguments = {}",
                joinPoint.getSignature().getName(),
                requestUrl,
                ipAddress,
                username,
                Arrays.toString(joinPoint.getArgs()));
    }

    // Log response details after the method executes successfully
    @AfterReturning(pointcut = "kz.balaguide.common_module.aop.pointcuts.LoggingPointcuts.loggingAllControllersPointcut()", returning = "result")
    public void logResponseDetails(JoinPoint joinPoint, Object result) {
        String ipAddress = request.getRemoteAddr();
        String requestUrl = request.getRequestURL().toString();
        String username = GetUsernameFromSecurityContextHolder.getUsername();

        HttpStatus status = result instanceof ResponseEntity
                ?
                HttpStatus.resolve(((ResponseEntity<?>) result).getStatusCode().value())
                :
                HttpStatus.OK;

        log.info("RESPONSE: Method = {}, URL = {}, IP Address = {}, Username = {}, Status = {}, Response = {}",
                joinPoint.getSignature().getName(),
                requestUrl,
                ipAddress,
                username,
                status,
                result
        );
    }

    //TODO: Нужна ли она?
    @AfterThrowing(pointcut = "kz.balaguide.common_module.aop.pointcuts.LoggingPointcuts.loggingAllControllersPointcut()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Throwable exception) {
        String ipAddress = request.getRemoteAddr();
        String requestUrl = request.getRequestURL().toString();
        String username = GetUsernameFromSecurityContextHolder.getUsername();

        log.error("EXCEPTION: Method = {}, URL = {}, IP Address = {}, Username = {}, Exception = {}, Message = {}",
                joinPoint.getSignature().getName(),
                requestUrl,
                ipAddress,
                username,
                exception.getClass().getName(),
                exception.getMessage()
        );
    }

    //TODO: Нужна ли она?
    /**
     * Logs information before the execution of methods annotated with {@link ForLog}.
     *
     * @param joinPoint provides reflective access to both the state available at a join point and the static part of the join point.
     * @param param the parameter passed to the method; can be null.
     */
    @Before("kz.balaguide.common_module.aop.pointcuts.LoggingPointcuts.loggingMethodsWhichAnnotatedByForLogAnnotationPointcut()  && args(param,..)")
    public void logBefore(JoinPoint joinPoint, Object param) {
        log.info("Before method execution");
        log.info("Method: {}", joinPoint.getSignature().toString());
        log.info("Parameters: {}", param != null ? param.toString() : "No parameters");
    }

    //TODO: Нужна ли она?
    /**
     * Logs execution time for methods annotated with {@link ForLog}.
     *
     * @param proceedingJoinPoint provides reflective access to both the state available at a join point and the static part of the join point.
     * @return the result of the method execution.
     * @throws Throwable if the method execution fails.
     */
    @Around("kz.balaguide.common_module.aop.pointcuts.LoggingPointcuts.loggingMethodsWhichAnnotatedByForLogAnnotationPointcut()")
    public Object logAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        try {
            return proceedingJoinPoint.proceed();
        } finally {
            long endTime = System.currentTimeMillis();
            log.info("After method execution");
            log.info("Method: {}", proceedingJoinPoint.getSignature().toString());
            log.info("Execution time: {} ms", (endTime - startTime));
        }
    }

    //TODO: Нужна ли она?
    /**
     * Logs information when a method annotated with {@link ForLog} throws an exception.
     *
     * @param joinPoint provides reflective access to both the state available at a join point and the static part of the join point.
     * @param exception the exception thrown by the method.
     */
    @AfterThrowing(pointcut = "kz.balaguide.common_module.aop.pointcuts.LoggingPointcuts.loggingMethodsWhichAnnotatedByForLogAnnotationPointcut()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        log.error("Method thrown an exception: {}", joinPoint.getSignature().toString());
        log.error("Exception message: {}", exception.getMessage());
    }
}
