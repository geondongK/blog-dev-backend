package blogservices.blogdevbackend.domain.like.dto;

import blogservices.blogdevbackend.domain.like.domain.Like;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeRequestDto {
    private Integer postId; // 게시물 PK

    private Integer commentId; // 댓글 PK

    private Long userId; // 사용자 PK

    public Like toEntity() {
        return Like.builder()
                .postId(postId)
                .commentId(commentId)
                .userId(userId)
                .build();
    }
}
