package kz.balaguide.common_module.exceptionhandling;

import kz.balaguide.common_module.core.entities.ResponseMetadata;
import kz.balaguide.common_module.core.enums.ResponseCode;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.generic.ChildNotEnrolledToCourseException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.*;
import kz.balaguide.common_module.services.responsemetadata.ResponseMetadataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.alreadyexists.UserAlreadyExistsException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.financialoperation.heirs.BalanceUpdateException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.financialoperation.heirs.InsufficientFundsException;
import kz.balaguide.common_module.core.exceptions.technical.UnauthorizedException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.generic.ChildNotBelongToParentException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.generic.CourseFullException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.generic.IneligibleChildException;
import kz.balaguide.common_module.core.dtos.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    private final ResponseMetadataService responseMetadataService;

    /**
     * GENERAL EXCEPTIONS
     */

    //Bad Request, нужно протестить
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<String>> handleBadRequestException(BadRequestException ex) {
        log.error("Bad request exception: {}, {}", ex.getMessage(), ex.getStackTrace());
        String cause = (ex.getCause() != null) ? ex.getCause().toString() : ex.getMessage();

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._0000);
        ApiResponse<String> apiResponse = ApiResponse.<String>builder()
                .responseMetadata(responseMetadata)
                .data(cause)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("Method argument not valid exception: {}, {}", ex.getMessage(), ex.getStackTrace());
        String cause = (ex.getCause() != null) ? ex.getCause().toString() : ex.getMessage();

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._0000);
        ApiResponse<String> apiResponse = ApiResponse.<String>builder()
                .responseMetadata(responseMetadata)
                .data(cause)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    //Runtime, нужно протестить
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<String>> handleRuntimeException(RuntimeException ex) {
        log.error("Runtime exception: ", ex);
        String cause = (ex.getCause() != null) ? ex.getCause().toString() : ex.getMessage();

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._0001);
        ApiResponse<String> apiResponse = ApiResponse.<String>builder()
                .responseMetadata(responseMetadata)
                .data(cause)
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("Illegal argument exception: {}, {}", ex.getMessage(), ex.getStackTrace());
        String cause = (ex.getCause() != null) ? ex.getCause().toString() : ex.getMessage();

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._0001);
        ApiResponse<String> apiResponse = ApiResponse.<String>builder()
                .responseMetadata(responseMetadata)
                .data(cause)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);

    }


    /**
     * BUSINESS LOGIC EXCEPTIONS
     */

    // FINANCIAL EXCEPTIONS:
    @ExceptionHandler(value = BalanceUpdateException.class)
    public ResponseEntity<ApiResponse<Void>> handleBalanceUpdateException(BalanceUpdateException ex) {
        log.error("Balance update exception: {}, {}", ex.getMessage(), ex.getStackTrace());
        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._0300);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .responseMetadata(responseMetadata)
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
    }

    @ExceptionHandler(value = InsufficientFundsException.class)
    public ResponseEntity<ApiResponse<Void>> handleInsufficientFundsException(InsufficientFundsException ex) {
        log.error("Insufficient funds exception: {}, {}", ex.getMessage(), ex.getStackTrace());
        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._0301);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .responseMetadata(responseMetadata)
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(apiResponse);
    }

    // GENERIC

    // Child not belong to parent
    @ExceptionHandler(value = ChildNotBelongToParentException.class)
    public ResponseEntity<ApiResponse<Void>> handleChildNotBelongToParentException(ChildNotBelongToParentException ex) {
        log.error("Child not belong to parent exception: {}, {}", ex.getMessage(), ex.getStackTrace());
        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._0400);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .responseMetadata(responseMetadata)
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    // Child not enrolled to course
    @ExceptionHandler(value = ChildNotEnrolledToCourseException.class)
    public ResponseEntity<ApiResponse<Void>> handleChildNotEnrolledToCourseException(ChildNotEnrolledToCourseException ex) {
        log.error("Child not enroll to course exception: {}, {}", ex.getMessage(), ex.getStackTrace());
        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._0401);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .responseMetadata(responseMetadata)
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @ExceptionHandler(value = CourseFullException.class)
    public ResponseEntity<ApiResponse<Void>> handleCourseFullException(CourseFullException ex) {
        log.error("Course already full exception: {}, {}", ex.getMessage(), ex.getStackTrace());
        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._0800);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .responseMetadata(responseMetadata)
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    // Ineligible child
    @ExceptionHandler(value = IneligibleChildException.class)
    public ResponseEntity<ApiResponse<Void>> handleIneligibleChildException(IneligibleChildException ex) {
        log.error("Ineligible child exception: {}, {}", ex.getMessage(), ex.getStackTrace());
        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._0402);
        ApiResponse<Void> apiResponse = new ApiResponse<>(responseMetadata, null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    // NOT FOUND

    // Child not found
    @ExceptionHandler(value = ChildNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleChildNotFoundException(ChildNotFoundException ex) {
        log.error("Child not found exception: {}, {}", ex.getMessage(), ex.getStackTrace());
        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._0100);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .responseMetadata(responseMetadata)
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    // Children not found
    @ExceptionHandler(value = ChildrenNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleChildrenNotFoundException(ChildrenNotFoundException ex) {
        log.error("Children not found: {}, {}", ex.getMessage(), ex.getStackTrace());
        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._0101);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .responseMetadata(responseMetadata)
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    // Course not found
    @ExceptionHandler(value = CourseNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleCourseNotFoundException(CourseNotFoundException ex) {
        log.error("Course not found exception: {}, {}", ex.getMessage(), ex.getStackTrace());
        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._0103);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .responseMetadata(responseMetadata)
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    // Education center not found
    @ExceptionHandler(value = EducationCenterNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleEducationCenterNotFoundException(EducationCenterNotFoundException ex) {
        log.error("Education center not found: {}, {}", ex.getMessage(), ex.getStackTrace());
        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._0104);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .responseMetadata(responseMetadata)
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    // Parent not found
    @ExceptionHandler(value = ParentNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleParentNotFoundException(ParentNotFoundException ex) {
        log.error("Parent not found exception: {}, {}", ex.getMessage(), ex.getStackTrace());
        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._0102);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .responseMetadata(responseMetadata)
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    // Already exists

    // User already exists
    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        log.error("User already exists exception: {}, {}", ex.getMessage(), ex.getStackTrace());
        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._0200);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .responseMetadata(responseMetadata)
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * TECHNICAL EXCEPTIONS:
     */

    // Unauthorized
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException ex) {
        log.error("Unauthorized exception: {}, {}", ex.getMessage(), ex.getStackTrace());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: " + ex.getMessage());
    }

}

