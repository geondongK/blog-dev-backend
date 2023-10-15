package blogservices.blogdevbackend.domain.post.controller;

import blogservices.blogdevbackend.domain.post.domain.Post;
import blogservices.blogdevbackend.domain.post.dto.PostRequestDto;
import blogservices.blogdevbackend.domain.post.dto.PostResponseDto;
import blogservices.blogdevbackend.domain.post.exception.Constants;
//import blogservices.blogdevbackend.domain.post.exception.PostException;
import blogservices.blogdevbackend.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
// 보안으로 auth 임시로 설정
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService service;

    /* 전체 게시물 */
    @GetMapping(value = "/post")
    public List<PostResponseDto> getAllPost(int limit, int offset) {
        return service.getAllPost(limit, offset);
    }

    /* 해당 게시물 */
    @GetMapping(value = "/post/{postId}")
    public List<PostResponseDto> getPost(@PathVariable long postId) {

        log.info("postController::postId = {}", postId);

        return service.getPost(postId);
    }

    /* 게시물 작성 */
    @PostMapping(value = "/post")
    public Long createPost(@RequestBody PostRequestDto request) {
        return service.createPost(request);
    }

    /* 게시물 수정 */
    @PutMapping(value = "/post/{postId}")
    public Long updatePost(@PathVariable long postId, @RequestBody PostRequestDto request) {
        return service.updatePost(postId, request);
    }

    /* 게시물 삭제 */
    @DeleteMapping(value = "/post/{postId}")
    public void deletePost(@PathVariable long postId) {
        service.deletePost(postId);
    }

    /* 게시물 검색 */
    @GetMapping(value = "/post/search")
    public List<PostResponseDto> searchPost(@RequestParam(value = "q", required = false) String q, int limit, int offset) {
        return service.searchPost(q, limit, offset);
    }

}
