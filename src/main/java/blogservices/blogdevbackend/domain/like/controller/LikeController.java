package blogservices.blogdevbackend.domain.like.controller;

import blogservices.blogdevbackend.domain.like.dto.LikeRequestDto;
import blogservices.blogdevbackend.domain.like.dto.LikeResponseDto;
import blogservices.blogdevbackend.domain.like.service.LikeService;
import blogservices.blogdevbackend.global.common.response.CommonResponse;
import blogservices.blogdevbackend.global.common.response.ResponseService;
import blogservices.blogdevbackend.global.common.response.SingleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class LikeController {

    private final LikeService likeService;
    private final ResponseService responseService;

    /* 좋아요 조회 */
    @GetMapping(value = "/like/{postId}")
    public SingleResponse<List<LikeResponseDto>> getLike(@PathVariable int postId) {
        List<LikeResponseDto> response = likeService.getLike(postId);
        return responseService.getSingResponse(response);
    }

    /* 좋아요 추가 */
    @PostMapping(value = "/like")
    public SingleResponse<List<LikeResponseDto>> addLike(@RequestBody LikeRequestDto request) {
        List<LikeResponseDto> response = likeService.addLike(request);
        return responseService.getSingResponse(response);
    }

    /* 좋아요 삭제 */
    @DeleteMapping(value = "/like/{commentId}/{userId}")
    public CommonResponse deleteLike(@PathVariable int commentId, @PathVariable long userId) {
        likeService.deleteLike(commentId, userId);
        return responseService.getSuccessResponse();
    }
}
