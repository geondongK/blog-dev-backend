package blogservices.blogdevbackend.domain.post.dto;

import blogservices.blogdevbackend.domain.post.domain.Post;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {

    private Long postId; // PK
    private Long writerId; // 작성자 PK
    private String title; // 제목
    private String description; // 내용
    private String writer; // 작성자
    private Integer view; // 조회수
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate; // 생성일
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDate; // 수정일

    public PostResponseDto(Post entity) {
        this.postId = entity.getPostId();
        this.writerId = entity.getWriterId();
        this.title = entity.getTitle();
        this.description = entity.getDescription();
        this.writer = entity.getWriter();
        this.view = entity.getView();
        this.createDate = entity.getCreateDate();
        this.updateDate = entity.getUpdateDate();
    }
}
