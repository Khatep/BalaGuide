package kz.balaguide.controllers.exceptionhandling;
import io.jsonwebtoken.ExpiredJwtException;
import kz.balaguide.core.entities.ResponseMetadata;
import kz.balaguide.core.enums.ResponseCode;
import kz.balaguide.core.exceptions.buisnesslogic.generic.ChildNotEnrolledToCourseException;
import kz.balaguide.core.exceptions.buisnesslogic.notfound.*;
import kz.balaguide.services.responsemetadata.ResponseMetadataService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import kz.balaguide.core.exceptions.buisnesslogic.alreadyexists.UserAlreadyExistsException;
import kz.balaguide.core.exceptions.buisnesslogic.financialoperation.heirs.BalanceUpdateException;
import kz.balaguide.core.exceptions.buisnesslogic.financialoperation.heirs.InsufficientFundsException;
import kz.balaguide.core.exceptions.technical.UnauthorizedException;
import kz.balaguide.core.exceptions.buisnesslogic.generic.ChildNotBelongToParentException;
import kz.balaguide.core.exceptions.buisnesslogic.generic.CourseFullException;
import kz.balaguide.core.exceptions.buisnesslogic.generic.IneligibleChildException;
import kz.balaguide.core.dtos.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
//TODO добавить логи в хэндлеры DIP-27
public class GlobalExceptionHandler {

    private final ResponseMetadataService responseMetadataService;

    /**GENERAL EXCEPTIONS*/

    //Bad Request, нужно протестить
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<String>> handleBadRequestException(BadRequestException ex) {

        String cause = ex.getCause().toString();

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._0000);
        ApiResponse<String> apiResponse = new ApiResponse<>(responseMetadata, cause);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    //Runtime, нужно протестить
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<String>> handleRuntimeException(RuntimeException ex) {

        String cause = ex.getCause().toString();

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._0100);
        ApiResponse<String> apiResponse = new ApiResponse<>(responseMetadata, cause);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }


    /**BUSINESS LOGIC EXCEPTIONS*/

    // FINANCIAL EXCEPTIONS:

    @ExceptionHandler(value = BalanceUpdateException.class)
    public ResponseEntity<ApiResponse<Void>> handleBalanceUpdateException(BalanceUpdateException ex) {

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._0300);
        ApiResponse<Void> apiResponse = new ApiResponse<>(responseMetadata, null);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
    }

    @ExceptionHandler(value = InsufficientFundsException.class)
    public ResponseEntity<ApiResponse<Void>> handleInsufficientFundsException(InsufficientFundsException ex) {

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._0301);
        ApiResponse<Void> apiResponse = new ApiResponse<>(responseMetadata, null);

        return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(apiResponse);
    }

    // GENERIC

    // Child not belong to parent
    @ExceptionHandler(value = ChildNotBelongToParentException.class)
    public ResponseEntity<ApiResponse<Void>> handleChildNotBelongToParentException(ChildNotBelongToParentException ex) {

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._0400);
        ApiResponse<Void> apiResponse = new ApiResponse<>(responseMetadata, null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    // Child not enrolled to course
    @ExceptionHandler(value = ChildNotEnrolledToCourseException.class)
    public ResponseEntity<ApiResponse<Void>> handleChildNotEnrolledToCourseException(ChildNotEnrolledToCourseException ex) {

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._0401);
        ApiResponse<Void> apiResponse = new ApiResponse<>(responseMetadata, null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @ExceptionHandler(value = CourseFullException.class)
    public ResponseEntity<ApiResponse<Void>> handleCourseFullException(CourseFullException ex) {

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._0800);
        ApiResponse<Void> apiResponse = new ApiResponse<>(responseMetadata, null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    // Ineligible child
    @ExceptionHandler(value = IneligibleChildException.class)
    public ResponseEntity<ApiResponse<Void>> handleIneligibleChildException(IneligibleChildException ex) {

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._0402);
        ApiResponse<Void> apiResponse = new ApiResponse<>(responseMetadata, null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    // NOT FOUND

    // Child not found
    @ExceptionHandler(value = ChildNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleChildNotFoundException(ChildNotFoundException ex) {

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._0100);
        ApiResponse<Void> apiResponse = new ApiResponse<>(responseMetadata, null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    // Children not found
    @ExceptionHandler(value = ChildrenNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleChildrenNotFoundException(ChildrenNotFoundException ex) {

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._0101);
        ApiResponse<Void> apiResponse = new ApiResponse<>(responseMetadata, null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    // Course not found
    @ExceptionHandler(value = CourseNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleCourseNotFoundException(CourseNotFoundException ex) {

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._0103);
        ApiResponse<Void> apiResponse = new ApiResponse<>(responseMetadata, null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    // Education center not found
    @ExceptionHandler(value = EducationCenterNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleEducationCenterNotFoundException(EducationCenterNotFoundException ex) {

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._0104);
        ApiResponse<Void> apiResponse = new ApiResponse<>(responseMetadata, null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    // Parent not found
    @ExceptionHandler(value = ParentNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleParentNotFoundException(ParentNotFoundException ex) {

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._0102);
        ApiResponse<Void> apiResponse = new ApiResponse<>(responseMetadata, null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    // Already exists

    // User already exists
    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._0200);
        ApiResponse<Void> apiResponse = new ApiResponse<>(responseMetadata, null);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }

    /**TECHNICAL EXCEPTIONS:*/

    // Jwt token expired
    @ExceptionHandler(value = ExpiredJwtException.class)
    public ResponseEntity<ApiResponse<Void>> handleExpiredJwtException(ExpiredJwtException ex) {

        //ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._0012);
        //ApiResponse<Void> apiResponse = new ApiResponse<>(responseMetadata, null);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }

    // Unauthorized
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: " + e.getMessage());
    }
}

