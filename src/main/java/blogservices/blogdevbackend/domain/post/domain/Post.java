package blogservices.blogdevbackend.domain.post.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId; // PK

    private Long writerId; // 작성자 PK

    private String title; // 제목

    private String description; // 내용

    private String writer; // 작성자

    private Integer view; // 조회수

    private LocalDateTime createDate = LocalDateTime.now(); // 생성일

    private LocalDateTime updateDate; // 수정일


    public void update(String title, String description) {
        this.title = title;
        this.description = description;
        this.updateDate = LocalDateTime.now();
    }

    @Builder
    public Post(Long writerId, String title, String description, String writer, Integer view) {
        this.writerId = writerId;
        this.title = title;
        this.description = description;
        this.writer = writer;
        this.view = view;
    }
}