package blogservices.blogdevbackend.domain.post.service;

import blogservices.blogdevbackend.domain.post.dto.PostRequestDTO;
import blogservices.blogdevbackend.domain.post.dto.PostResponseDTO;

import java.util.List;


public interface PostService {

    List<PostResponseDTO> getAllPost();

    List<PostResponseDTO> getPost(long postId);

    Long createPost(PostRequestDTO postRequestDTO);

    Long updatePost(Long postId, PostRequestDTO postRequestDTO);

    void deletePost(long postId);

}
