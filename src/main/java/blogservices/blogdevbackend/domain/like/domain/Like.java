package blogservices.blogdevbackend.domain.like.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 11)
    private Integer likeId; // PK
    @Column(length = 11)
    private Integer postId; // 게시물 PK
    @Column(length = 11)
    private Integer commentId; // 댓글 PK
    @Column(length = 20)
    private Long userId; // 사용자 PK

    @Builder
    public Like(Integer postId, Integer commentId, Long userId) {
        this.postId = postId;
        this.commentId = commentId;
        this.userId = userId;
    }

}
