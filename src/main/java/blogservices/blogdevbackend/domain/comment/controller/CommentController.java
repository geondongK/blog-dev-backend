package blogservices.blogdevbackend.domain.comment.controller;

import blogservices.blogdevbackend.domain.comment.dto.CommentRequestDto;
import blogservices.blogdevbackend.domain.comment.dto.CommentResponseDto;
import blogservices.blogdevbackend.domain.comment.service.CommentService;
import blogservices.blogdevbackend.global.common.response.CommonResponse;
import blogservices.blogdevbackend.global.common.response.ListResponse;
import blogservices.blogdevbackend.global.common.response.ResponseService;
import blogservices.blogdevbackend.global.common.response.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final ResponseService responseService;

    /* 댓글 작성 */
    @PostMapping(value = "/comment")
    public SingleResponse<List<CommentResponseDto>> createComment(@RequestBody @Valid CommentRequestDto request) {
        List<CommentResponseDto> response = commentService.save(request);
        return responseService.getSingResponse(response);
    }

    /* 해당 댓글 */
    @GetMapping(value = "/comment/{postId}")
    public ListResponse<CommentResponseDto> getComment(@PathVariable int postId) {
        List<CommentResponseDto> response = commentService.getComment(postId);
        return responseService.getListResponse(response);
    }

    /* 댓글 수정 */
    @PutMapping(value = "/comment/{commentId}")
    public CommonResponse updateComment(@PathVariable long commentId, @RequestBody CommentRequestDto request) {
        Long response = commentService.update(commentId, request);
        return responseService.getSuccessResponse();
    }

    /* 대댓글 존재 시 isDeleted 업데이트 */
    @PutMapping(value = "/comment/existComment/{commentId}")
    public CommonResponse existsByComment(@PathVariable long commentId) {
        Long response = commentService.existsByComment(commentId);
        return responseService.getSuccessResponse();
    }

    /* 댓글 삭제 */
    @DeleteMapping(value = "/comment/{commentId}")
    public CommonResponse deleteComment(@PathVariable long commentId) {
        commentService.deleteComment(commentId);
        return responseService.getSuccessResponse();
    }
}
