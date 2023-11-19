package blogservices.blogdevbackend.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.JDBCException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.sql.SQLException;

@Slf4j
@RestControllerAdvice // 전역 예외 처리
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ConstraintViolationException.class, DataIntegrityViolationException.class})
    // hibernate 에러
    protected ResponseEntity<Object> handleDataException() {
        // log.error("handleCustomException throw CustomException : {}", GlobalExceptionResponseCode.VALIDATION_FAILED);
        GlobalExceptionResponseCode errorCode = GlobalExceptionResponseCode.DUPLICATE_RESOURCE;
        return ResponseEntity.status(errorCode.getHttpStatus()).body(errorCode);
    }

    @Override
    /* 검증 에러

      @ExceptionHandler에 이미 MethodArgumentNotValidException이 구현되어있기
      때문에 GlobalExceptionHandler에서 동일한 예외 처리를 하게 되면, Ambiguous(모호성) 문제가 발생

    */
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        GlobalExceptionResponseCode errorCode = GlobalExceptionResponseCode.VALIDATION_FAILED;
        return ResponseEntity.status(errorCode.getHttpStatus()).body(new GlobalExceptionResponse(errorCode));
    }

    @ExceptionHandler(value = {GlobalException.class})
    // 특정 에러
    protected ResponseEntity<Object> handleGlobalException(GlobalException e) {
        // GlobalExceptionResponseCode errorCode;
        log.error("handleCustomException throw CustomException : {}", e.getErrorCode());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(new GlobalExceptionResponse(e.getErrorCode()));
    }

    @ExceptionHandler(value = {JDBCException.class, SQLException.class})
    protected ResponseEntity<Object> handleSqlException(GlobalException e) {
        GlobalExceptionResponseCode errorCode = GlobalExceptionResponseCode.DATABASE_ERROR;
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(new GlobalExceptionResponse(errorCode));
    }
}
