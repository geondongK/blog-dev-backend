package blogservices.blogdevbackend.domain.comment.dto;

import blogservices.blogdevbackend.domain.comment.domain.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long commentId; // PK
    private Long postId; // 게시물 PK
    private Long writerId; // 작성자 PK
    private Long parentId; // 부모 댓글 PK
    private Integer commentGroup; // 부모 댓글 PK
    private Integer seq; // 순서
    private String description; // 내용
    private String writer; // 작성자
    private Character isDeleted; // 삭제 여부
    private LocalDateTime createDate; // 생성일
    private LocalDateTime updateDate; // 수정일

    public CommentResponseDto(Comment entity) {
        this.commentId = entity.getCommentId();
        this.postId = entity.getPostId();
        this.writerId = entity.getWriterId();
        this.parentId = entity.getParentId();
        this.commentGroup = entity.getCommentGroup();
        this.seq = entity.getSeq();
        this.description = entity.getDescription();
        this.writer = entity.getWriter();
        this.isDeleted = entity.getIsDeleted();
        this.createDate = entity.getCreateDate();
        this.updateDate = entity.getUpdateDate();
    }

}
