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
    public Long createComment(@RequestBody CommentRequestDto request) {
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

    /* 댓글 삭제 */
    @DeleteMapping(value = "/comment/{commentId}")
    public ResponseEntity<ResultDtoUtil<String>> deleteComment(@PathVariable long commentId) {
        service.deleteComment(commentId);
        return ResponseEntity.ok(ResultDtoUtil.response(HttpStatus.OK, HttpStatus.OK.toString(), "댓글 삭제 성공"));
    }
}
