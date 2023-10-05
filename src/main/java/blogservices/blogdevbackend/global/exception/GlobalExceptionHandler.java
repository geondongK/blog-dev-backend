package blogservices.blogdevbackend.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ConstraintViolationException.class, DataIntegrityViolationException.class})
    protected ResponseEntity<GlobalErrorResponse> handleDataException() {
        log.error("handleDataException throw Exception : {}", GlobalErrorCode.DUPLICATE_RESOURCE);
        return GlobalErrorResponse.toResponseEntity(GlobalErrorCode.DUPLICATE_RESOURCE);
    }

    @ExceptionHandler(value = {GlobalException.class})
    protected ResponseEntity<GlobalErrorResponse> handleGlobalException(GlobalException e) {
        log.error("handleCustomException throw CustomException : {}", e.getErrorCode());
        return GlobalErrorResponse.toResponseEntity(e.getErrorCode());
    }


}
