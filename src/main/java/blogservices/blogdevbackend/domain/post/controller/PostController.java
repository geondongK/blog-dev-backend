package blogservices.blogdevbackend.domain.post.controller;

import blogservices.blogdevbackend.domain.post.dto.PostRequestDTO;
import blogservices.blogdevbackend.domain.post.dto.PostResponseDTO;
import blogservices.blogdevbackend.domain.post.exception.Constants;
import blogservices.blogdevbackend.domain.post.exception.PostException;
import blogservices.blogdevbackend.domain.post.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
// @RequiredArgsConstructor 생성자 주입
public class PostController {

    private final Logger LOGGER = LoggerFactory.getLogger(PostController.class);

    private PostService service;

    public PostController(PostService postService) {
        this.service = postService;
    }

    @PostMapping(value = "/test")
    public void exceptionTest() throws PostException {
        throw new PostException(Constants.ExceptionClass.POST, HttpStatus.FORBIDDEN, "접근이 금지되었습니다.");
    }


    /* 전체 게시물 */
    @GetMapping(value = "/posts")
    public List<PostResponseDTO> getAllPosts() {
        return service.getAllPost();
    }

    /* 해당 게시물 */
    @GetMapping(value = "/posts/{postId}")
    public List<PostResponseDTO> getPost(@PathVariable long postId) {

        // long startTime = System.currentTimeMillis(); = 처리과정 시간 측정
        // LOGGER.info("[PostController] Response :: postId = {}", postId);
        LOGGER.info("postId = {}", postId);

        List<PostResponseDTO> postDTO = service.getPost(postId);
        return postDTO;
    }

    /* 게시물 작성 */
    @PostMapping(value = "/posts")
    public Long createPost(@RequestBody PostRequestDTO postRequestDTO) {
        return service.createPost(postRequestDTO);
    }

    /* 게시물 수정 */
    @PutMapping(value = "/posts/{postId}")
    public Long updatePost(@PathVariable long postId, @RequestBody PostRequestDTO postRequestDTO) {
        return service.updatePost(postId, postRequestDTO);
    }

    /* 게시물 삭제 */
    @DeleteMapping(value = "/posts/{postId}")
    public void deletePost(@PathVariable long postId) {
        service.deletePost(postId);
    }
}
