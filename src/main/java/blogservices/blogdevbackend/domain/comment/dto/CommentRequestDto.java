package blogservices.blogdevbackend.domain.comment.dto;

import blogservices.blogdevbackend.domain.comment.domain.Comment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRequestDto {
    private Integer postId; // 게시물 PK
    private Long writerId; // 작성자 PK
    private Long parentId; // 부모 댓글 PK
    private Integer commentGroup; // 부모 댓글 PK
    @NotBlank
    private String description; // 내용
    private String writer; // 작성자

    public Comment toEntity() {
        return Comment.builder()
                .postId(postId)
                .writerId(writerId)
                .parentId(parentId)
                .commentGroup(commentGroup)
                .description(description)
                .writer(writer)
                .build();
    }

}
