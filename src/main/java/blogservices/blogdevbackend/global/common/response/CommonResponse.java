package blogservices.blogdevbackend.global.common.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// 응답 API
public class CommonResponse {
    private boolean success;
    private int code;
    private String message;
}
