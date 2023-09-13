package blogservices.blogdevbackend.domain.post.dto;

import blogservices.blogdevbackend.domain.post.domain.Post;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequestDTO {

    @NotNull
    private Long postId;
    @NotNull
    private Long postUserId;
    @NotBlank(message = "제목을 작성해 주세요.")
    private String postTitle;
    @NotBlank(message = "본문을 작성해 주세요.")
    private String postDescription;
    @NotNull
    private String postName;

    private Long postView;
    private Character checkDelete;
    @NotNull
    private LocalDate postCreateDate;

    @Builder
    public PostRequestDTO(Long postUserId, String postTitle, String postDescription
            , String postName, Long postView, Character checkDelete, LocalDate postCreateDate) {
        this.postUserId = postUserId;
        this.postTitle = postTitle;
        this.postDescription = postDescription;
        this.postName = postName;
        this.postView = postView;
        this.postCreateDate = postCreateDate;
        this.checkDelete = checkDelete;

    }

    // DTO to Entity
    public Post toEntity() {
        return Post.builder()
                .postId(postId)
                .postUserId(postUserId)
                .postTitle(postTitle)
                .postDescription(postDescription)
                .postName(postName)
                .postView(postView)
                .checkDelete(checkDelete)
                .postCreateDate(postCreateDate)
                .build();
    }

}
