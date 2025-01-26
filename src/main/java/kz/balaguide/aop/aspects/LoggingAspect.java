package kz.balaguide.aop.aspects;

import jakarta.servlet.http.HttpServletRequest;
import kz.balaguide.core.annotations.ForLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class LoggingAspect {

    private final HttpServletRequest request;

    // Log request details before the method executes
    @Before("execution(* kz.balaguide.controllers.*.*(..))")
    public void logRequestDetails(JoinPoint joinPoint) {
        String ipAddress = request.getRemoteAddr();
        String requestUrl = request.getRequestURL().toString();
        String username = getUsername();

        log.info("REQUEST: Method = {}, URL = {}, IP Address = {}, Username = {}, Arguments = {}",
                joinPoint.getSignature().getName(),
                requestUrl,
                ipAddress,
                username,
                Arrays.toString(joinPoint.getArgs()));
    }

    // Log response details after the method executes successfully
    @AfterReturning(pointcut = "execution(* kz.balaguide.controllers.*.*(..))", returning = "result")
    public void logResponseDetails(JoinPoint joinPoint, Object result) {
        String ipAddress = request.getRemoteAddr();
        String requestUrl = request.getRequestURL().toString();
        String username = getUsername();

        HttpStatus status = result instanceof ResponseEntity ?
                HttpStatus.resolve(((ResponseEntity<?>) result).getStatusCode().value()) : HttpStatus.OK;

        log.info("RESPONSE: Method = {}, URL = {}, IP Address = {}, Username = {}, Status = {}, Response = {}",
                joinPoint.getSignature().getName(),
                requestUrl,
                ipAddress,
                username,
                status,
                result);
    }

    @AfterThrowing(pointcut = "execution(* kz.balaguide.controllers.*.*(..))", throwing = "exception")
    public void logException(JoinPoint joinPoint, Throwable exception) {
        String ipAddress = request.getRemoteAddr();
        String requestUrl = request.getRequestURL().toString();
        String username = getUsername();

        log.error("EXCEPTION: Method = {}, URL = {}, IP Address = {}, Username = {}, Exception = {}, Message = {}",
                joinPoint.getSignature().getName(),
                requestUrl,
                ipAddress,
                username,
                exception.getClass().getName(),
                exception.getMessage());
    }


    private String getUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userdetails) {
            return userdetails.getUsername();
        } else {
            return principal.toString();
        }
    }

    /**
     * Logs information before the execution of methods annotated with {@link ForLog}.
     *
     * @param joinPoint provides reflective access to both the state available at a join point and the static part of the join point.
     * @param param the parameter passed to the method; can be null.
     */
    @Before("@annotation(kz.balaguide.core.annotations.ForLog) && args(param,..)")
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
    @Around("@annotation(kz.balaguide.core.annotations.ForLog)")
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
    @AfterThrowing(pointcut = "@annotation(kz.balaguide.core.annotations.ForLog)", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        log.error("Method thrown an exception: {}", joinPoint.getSignature().toString());
        log.error("Exception message: {}", exception.getMessage());
    }
}
