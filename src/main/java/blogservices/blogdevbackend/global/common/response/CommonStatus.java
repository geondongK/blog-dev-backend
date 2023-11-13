package blogservices.blogdevbackend.global.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonStatus {
    SUCCESS(1, "success"),
    FAIL(0, "fail");

    private int code;
    private String message;
}
