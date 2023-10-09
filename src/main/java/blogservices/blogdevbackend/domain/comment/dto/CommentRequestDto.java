package blogservices.blogdevbackend.domain.comment.dto;

import blogservices.blogdevbackend.domain.comment.domain.Comment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRequestDto {
    private Long postId; // 게시물 PK
    private Long writerId; // 작성자 PK
    private Long parentId; // 부모 댓글 PK
    private Integer commentGroup; // 부모 댓글 PK
    private Integer seq; // 순서
    private String description; // 내용
    private String writer; // 작성자
    private Character isDeleted; // 삭제여부

    public Comment toEntity() {
        return Comment.builder()
                .postId(postId)
                .writerId(writerId)
                .parentId(parentId)
                .commentGroup(commentGroup)
                .seq(seq)
                .description(description)
                .writer(writer)
                .isDeleted(isDeleted)
                .build();
    }

}
