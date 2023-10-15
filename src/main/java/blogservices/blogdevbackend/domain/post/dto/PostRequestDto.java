package blogservices.blogdevbackend.domain.post.dto;

import blogservices.blogdevbackend.domain.post.domain.Post;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequestDto {
    //    @NotNull
    private Long writerId; // 작성자 PK
    @NotBlank(message = "제목을 작성해 주세요.")
    private String title; // 제목
    @NotBlank(message = "본문을 작성해 주세요.")
    private String description; // 내용
    //    @NotNull
    private String writer; // 작성자


    // DTO to Entity
    public Post toEntity() {
        return Post.builder()
                .writerId(writerId)
                .title(title)
                .description(description)
                .writer(writer)
                .build();
    }

}
