package blogservices.blogdevbackend.domain.comment.domain;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long commentId; // PK
    private Integer postId; // 게시물 PK
    private Long writerId; // 작성자 PK
    private Long parentId; // 부모 댓글 PK
    private Integer commentGroup; // 부모 댓글 PK
    private Integer seq; // 순서
    @Column(columnDefinition = "TEXT")
    private String description; // 내용
    @Column(length = 8)
    private String writer; // 작성자
    private Boolean isDeleted; // 삭제 여부
    private LocalDateTime createDate = LocalDateTime.now(); // 생성일
    private LocalDateTime updateDate; // 수정일

    public void update(String description) {
        this.description = description;
        this.updateDate = LocalDateTime.now();
    }

    public void deleteUpdate() {
        this.isDeleted = true;
    }


    @Builder
    public Comment(Integer postId, Long writerId
            , Long parentId, Integer commentGroup, Integer seq,
                   String description, String writer, Boolean isDeleted) {
        this.postId = postId;
        this.writerId = writerId;
        this.parentId = parentId;
        this.commentGroup = commentGroup;
        this.seq = seq;
        this.description = description;
        this.writer = writer;
        this.isDeleted = isDeleted;
    }
}

