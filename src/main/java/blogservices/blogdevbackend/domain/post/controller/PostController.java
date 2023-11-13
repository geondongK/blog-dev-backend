package blogservices.blogdevbackend.domain.post.controller;

import blogservices.blogdevbackend.domain.post.dto.PostRequestDto;
import blogservices.blogdevbackend.domain.post.dto.PostResponseDto;
import blogservices.blogdevbackend.domain.post.service.PostService;
import blogservices.blogdevbackend.global.common.response.CommonResponse;
import blogservices.blogdevbackend.global.common.response.ListResponse;
import blogservices.blogdevbackend.global.common.response.ResponseService;
import blogservices.blogdevbackend.global.common.response.SingleResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;
    private final ResponseService responseService;

    /* 전체 게시물 */
    @GetMapping(value = "/post")
    public ListResponse<PostResponseDto> getAllPost(int limit, int offset) {
        List<PostResponseDto> response = postService.getAllPost(limit, offset);
        return responseService.getListResponse(response);
    }

    /* 해당 게시물 */
    @GetMapping(value = "/post/{postId}")
    public SingleResponse<List<PostResponseDto>> getPost(@PathVariable long postId) {
        List<PostResponseDto> response = postService.getPost(postId);
        return responseService.getSingResponse(response);
    }

    /* 게시물 작성 */
    @PostMapping(value = "/post")
    public CommonResponse createPost(@RequestBody @Valid PostRequestDto request) {
        Long response = postService.createPost(request);
        return responseService.getSuccessResponse();
    }

    /* 게시물 수정 */
    @PutMapping(value = "/post/{postId}")
    public CommonResponse updatePost(@PathVariable long postId, @RequestBody PostRequestDto request) {
        Long response = postService.updatePost(postId, request);
        return responseService.getSuccessResponse();
    }

    /* 게시물 삭제 */
    @DeleteMapping(value = "/post/{postId}")
    public CommonResponse deletePost(@PathVariable long postId) {
        postService.deletePost(postId);
        return responseService.getSuccessResponse();
    }

    /* 게시물 검색 */
    @GetMapping(value = "/post/search")
    public SingleResponse<List<PostResponseDto>> searchPost(@RequestParam(value = "q", required = false) String q, int limit, int offset) {
        List<PostResponseDto> response = postService.searchPost(q, limit, offset);
        return responseService.getSingResponse(response);
    }

    /* 파일 업로드 */
    @PostMapping(value = "/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(postService.uploadFile(file));
    }

}
