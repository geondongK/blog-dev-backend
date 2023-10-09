package blogservices.blogdevbackend.domain.comment.service;

import blogservices.blogdevbackend.domain.comment.dto.CommentRequestDto;
import blogservices.blogdevbackend.domain.comment.dto.CommentResponseDto;

import java.util.List;

public interface CommentService {

    Long save(CommentRequestDto request);

    List<CommentResponseDto> getComment(long postId);

    Long update(Long commentId, CommentRequestDto request);

    void deleteComment(long commentId);

}
