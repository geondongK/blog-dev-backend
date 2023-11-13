package blogservices.blogdevbackend.domain.post.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId; // PK

    private Long writerId; // 작성자 PK

    @Column(length = 50)
    private String title; // 제목

    @Column(columnDefinition = "TEXT")
    private String description; // 내용

    @Column(length = 8)
    private String writer; // 작성자

    private Integer hits; // 조회수

    private LocalDateTime createDate = LocalDateTime.now(); // 생성일

    private LocalDateTime updateDate; // 수정일

    @PrePersist
    public void prePersist() {
        this.hits = this.hits == null ? 0 : this.hits; // 조회 수 0 초기화
    }


    public void update(String title, String description) {
        this.title = title;
        this.description = description;
        this.updateDate = LocalDateTime.now();
    }

    // 조회 수 증가
    public void increaseHits() {
        this.hits++;
    }

    @Builder
    public Post(Long writerId, String title, String description, String writer, Integer hits) {
        this.writerId = writerId;
        this.title = title;
        this.description = description;
        this.writer = writer;
        this.hits = hits;
    }
}