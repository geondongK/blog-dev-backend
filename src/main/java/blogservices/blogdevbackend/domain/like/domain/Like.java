package blogservices.blogdevbackend.domain.like.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "likes")
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer likeId; // PK
    private Integer postId; // 게시물 PK
    private Integer commentId; // 댓글 PK
    private Long userId; // 사용자 PK

    @Builder
    public Like(Integer postId, Integer commentId, Long userId) {
        this.postId = postId;
        this.commentId = commentId;
        this.userId = userId;
    }

}
