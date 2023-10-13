package blogservices.blogdevbackend.domain.comment.service;

import blogservices.blogdevbackend.domain.comment.domain.Comment;
import blogservices.blogdevbackend.domain.comment.dto.CommentRequestDto;
import blogservices.blogdevbackend.domain.comment.dto.CommentResponseDto;
import blogservices.blogdevbackend.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;


    /* 댓글 작성 */
    @Override
    public List<CommentResponseDto> save(CommentRequestDto request) {

        List<Comment> entity = Collections.singletonList(repository.save(request.toEntity()));

        return entity.stream().map(CommentResponseDto::new).collect(Collectors.toList());

    }

    /* 특정 댓글 조회 */
    @Override
    public List<CommentResponseDto> getComment(long postId) {
        List<Comment> entity = repository.findByPostId(postId);

        log.info("commetService::getComment = {}", postId);

        return entity.stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }

    /* 댓글 수정 */
    @Override
    public Long update(long commentId, CommentRequestDto request) {

        log.info("comment update = {}", request);

        Comment entity = repository.findById(commentId).orElseThrow();
        entity.update(request.getDescription());

        return commentId;
    }

    /* 대댓글 존재 시 isDeleted 업데이트 */
    @Override
    public Long existsByComment(long commentId) {

        Comment entity = repository.getReferenceById(commentId);

        log.info("deleteId = {} ", String.valueOf(entity.getCommentId()));

        entity.deleteUpdate();

        return commentId;
    }

    /* 댓글 삭제 */
    @Override
    public void deleteComment(long commentId) {
        repository.deleteById(commentId);
    }
}
