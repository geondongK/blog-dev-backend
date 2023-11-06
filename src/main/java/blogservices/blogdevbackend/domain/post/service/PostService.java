package blogservices.blogdevbackend.domain.post.service;

import blogservices.blogdevbackend.domain.post.domain.Post;
import blogservices.blogdevbackend.domain.post.dto.PostRequestDto;
import blogservices.blogdevbackend.domain.post.dto.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface PostService {

    //List<PostResponseDto> getAllPost(int limit, int offset);
    List<PostResponseDto> getAllPost(int limit, int offset);

    List<PostResponseDto> getPost(long postId);

    Long createPost(PostRequestDto postRequestDTO);

    Long updatePost(Long postId, PostRequestDto postRequestDTO);

    void deletePost(long postId);

    List<PostResponseDto> searchPost(String keyword, int limit, int offset);

    String uploadFile(MultipartFile file);


}
