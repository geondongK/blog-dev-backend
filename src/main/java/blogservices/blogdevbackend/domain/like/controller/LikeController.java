package blogservices.blogdevbackend.domain.like.controller;

import blogservices.blogdevbackend.domain.like.dto.LikeRequestDto;
import blogservices.blogdevbackend.domain.like.dto.LikeResponseDto;
import blogservices.blogdevbackend.domain.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// 보안으로 auth 임시로 설정
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class LikeController {

    private final LikeService service;

    /* 좋아요 조회 */
    @GetMapping(value = "/like/{postId}")
    public List<LikeResponseDto> getLike(@PathVariable int postId) {
        return service.getLike(postId);
    }

    /* 좋아요 추가 */
    @PostMapping(value = "/like")
    public List<LikeResponseDto> addLike(@RequestBody LikeRequestDto request) {
        return service.addLike(request);
    }

    /* 좋아요 삭제 */
    @DeleteMapping(value = "/like/{commentId}/{userId}")
    public void deleteLike(@PathVariable int commentId, @PathVariable long userId) {
        service.deleteLike(commentId, userId);
    }
}
