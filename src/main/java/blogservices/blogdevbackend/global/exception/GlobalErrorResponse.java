package blogservices.blogdevbackend.global.exception;

import lombok.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class GlobalErrorResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final String code;
    private final String message;

    public static ResponseEntity<GlobalErrorResponse> toResponseEntity(GlobalErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(GlobalErrorResponse.builder()
                        .status(errorCode.getHttpStatus().value())
                        .error(errorCode.getHttpStatus().name())
                        .code(errorCode.name())
                        .message(errorCode.getDetailMessage())
                        .build()
                );
    }
}
