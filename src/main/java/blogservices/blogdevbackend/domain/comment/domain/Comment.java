package blogservices.blogdevbackend.domain.comment.domain;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "comment")
// 유효성 검사도 추가해야함.
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId; // PK

    @Column(length = 100)
    private Long postId; // 게시물 PK

    @Column(length = 20)
    private Long writerId; // 작성자 PK

    @Column(length = 20)
    private Long parentId; // 부모 댓글 PK

    @Column(length = 10)
    private Integer commentGroup; // 부모 댓글 PK

    @Column(length = 100)
    private Integer seq; // 순서

    @Column(columnDefinition = "TEXT")
    private String description; // 내용

    @Column(length = 100)
    private String writer; // 작성자

    private Character isDeleted; // 삭제 여부

    private LocalDateTime createDate = LocalDateTime.now(); // 생성일
    private LocalDateTime updateDate; // 수정일

    public void update(String description) {
        this.description = description;
        this.updateDate = LocalDateTime.now();
    }


    @Builder
    public Comment(Long postId, Long writerId
            , Long parentId, Integer commentGroup, Integer seq,
                   String description, String writer, Character isDeleted) {
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

