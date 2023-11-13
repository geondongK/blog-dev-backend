package blogservices.blogdevbackend.global.exception;

import lombok.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
public class GlobalExceptionResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final String code;
    private final String message;

    public GlobalExceptionResponse(GlobalExceptionResponseCode responseCode) {
        this.status = responseCode.getHttpStatus().value();
        this.error = responseCode.getHttpStatus().name();
        this.code = responseCode.name();
        this.message = responseCode.getDetailMessage();
    }
}
