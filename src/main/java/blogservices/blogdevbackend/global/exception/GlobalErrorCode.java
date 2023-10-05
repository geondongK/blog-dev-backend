package blogservices.blogdevbackend.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GlobalErrorCode {

    /* Auth 에러코드 */
    /* 404 NOT_FOUND : 잘못된 요청 */
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "이메일 또는 비밀번호가 틀렸습니다."),
    USER_EXISTS_FOUND(HttpStatus.NOT_FOUND, "이미 가입되어 있는 유저입니다"),


    /* 공통 */
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "데이터가 이미 존재합니다.");;

    private final HttpStatus httpStatus;
    private final String detailMessage;


}
