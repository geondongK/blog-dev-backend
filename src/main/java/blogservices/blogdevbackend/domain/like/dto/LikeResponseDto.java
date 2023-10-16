package blogservices.blogdevbackend.domain.like.dto;

import blogservices.blogdevbackend.domain.like.domain.Like;
import lombok.Getter;

@Getter
public class LikeResponseDto {
    private Integer likeId; // PK
    private Integer postId; // 게시물 PK
    private Integer commentId; // 댓글 PK
    private Long userId; // 사용자 PK

    public LikeResponseDto(Like entity) {
        this.likeId = entity.getLikeId();
        this.postId = entity.getPostId();
        this.commentId = entity.getCommentId();
        this.userId = entity.getUserId();
    }
}
