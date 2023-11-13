package blogservices.blogdevbackend.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GlobalExceptionResponseCode {
    /* 공통 */
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "데이터가 이미 존재합니다"),
    VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, "유효성 검증에 실패하였습니다"),

    /* 유저 */
    EMAIL_EXISTS_FOUND(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다"),
    NAME_EXISTS_FOUND(HttpStatus.CONFLICT, "이미 존재하는 별명입니다"),
    PASSWORD_NOT_SAME(HttpStatus.CONFLICT, "비밀번호가 일치하지 않습니다"),
    USER_NOT_FOUND(HttpStatus.CONFLICT, "이메일 또는 비밀번호가 틀렸습니다"),

    /* 인증 */
    AUTHORIZATION_FAILED(HttpStatus.UNAUTHORIZED, "인증에 실패하였습니다"),
    FORBIDDEN_FAILED(HttpStatus.FORBIDDEN, "권한이 없습니다"),

    /* Jwt */
    REFRESH_TOKEN_NOT_EXIST(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 유효하지 않습니다"),

    /* 서버 에러 */
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다");

    private final HttpStatus httpStatus;
    private final String detailMessage;

}
