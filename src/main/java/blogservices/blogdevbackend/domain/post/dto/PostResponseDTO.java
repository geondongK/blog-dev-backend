package blogservices.blogdevbackend.domain.post.dto;

import blogservices.blogdevbackend.domain.post.domain.Post;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PostResponseDTO {

    private Long postId;
    private Long postUserId;
    private String postTitle;
    private String postDescription;
    private String postName;
    private Long postView;
    private LocalDate postCreateDate;

    public PostResponseDTO(Post entity) {
        this.postId = entity.getPostId();
        this.postUserId = entity.getPostUserId();
        this.postTitle = entity.getPostTitle();
        this.postDescription = entity.getPostDescription();
        this.postName = entity.getPostName();
        this.postView = entity.getPostView();
        this.postCreateDate = LocalDate.from(entity.getPostCreateDate());
    }
}
