package blogservices.blogdevbackend.domain.post.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    private Long postUserId;
    private String postTitle;
    private String postDescription;
    private String postName;
    private Long postView;
    private Character checkDelete;
    private LocalDateTime postCreateDate = LocalDateTime.now();


    @Builder
    public Post(Long postId, Long postUserId, String postTitle, String postDescription, String postName, Long postView, Character checkDelete, LocalDate postCreateDate) {
        this.postId = postId;
        this.postUserId = postUserId;
        this.postTitle = postTitle;
        this.postDescription = postDescription;
        this.postName = postName;
        this.postView = postView;
        this.checkDelete = checkDelete;
        this.postCreateDate = postCreateDate.atStartOfDay();
    }

    public void update(String postTitle, String postDescription) {
        this.postTitle = postTitle;
        this.postDescription = postDescription;
    }
    
}