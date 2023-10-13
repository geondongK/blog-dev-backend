package blogservices.blogdevbackend.domain.comment.service;

import blogservices.blogdevbackend.domain.comment.dto.CommentRequestDto;
import blogservices.blogdevbackend.domain.comment.dto.CommentResponseDto;

import java.util.List;

public interface CommentService {

    List<CommentResponseDto> save(CommentRequestDto request);

    List<CommentResponseDto> getComment(long postId);

    Long update(long commentId, CommentRequestDto request);

    Long existsByComment(long commentId);

    void deleteComment(long commentId);

}
