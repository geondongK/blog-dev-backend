package blogservices.blogdevbackend.domain.comment.controller;

import blogservices.blogdevbackend.domain.comment.dto.CommentRequestDto;
import blogservices.blogdevbackend.domain.comment.dto.CommentResponseDto;
import blogservices.blogdevbackend.domain.comment.service.CommentService;
import blogservices.blogdevbackend.global.util.ResultDtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// 보안으로 auth 임시로 설정
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService service;

    /* 댓글 작성 */
    @PostMapping(value = "/comment")
    public List<CommentResponseDto> createComment(@RequestBody CommentRequestDto request) {
        return service.save(request);
    }

    /* 해당 댓글 */
    @GetMapping(value = "/comment/{postId}")
    public List<CommentResponseDto> getComment(@PathVariable long postId) {
        return service.getComment(postId);
    }

    /* 댓글 수정 */
    @PutMapping(value = "/comment/{commentId}")
    public Long updateComment(@PathVariable long commentId, @RequestBody CommentRequestDto request) {
        return service.update(commentId, request);
    }

    /* 대댓글 존재 시 isDeleted 업데이트 */
    @PutMapping(value = "/comment/existComment/{commentId}")
    public Long existsByComment(@PathVariable long commentId) {
        return service.existsByComment(commentId);
    }

    /* 댓글 삭제 */
    @DeleteMapping(value = "/comment/{commentId}")
    public void deleteComment(@PathVariable long commentId) {
        service.deleteComment(commentId);
    }
}
