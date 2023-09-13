package blogservices.blogdevbackend.domain.post.service;

import blogservices.blogdevbackend.domain.post.domain.Post;
import blogservices.blogdevbackend.domain.post.dto.PostRequestDTO;
import blogservices.blogdevbackend.domain.post.dto.PostResponseDTO;
import blogservices.blogdevbackend.domain.post.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final Logger LOGGER = LoggerFactory.getLogger(PostService.class);

    private PostRepository repository;

    @Autowired
    public PostServiceImpl(PostRepository repository) {
        this.repository = repository;
    }

    public List<PostResponseDTO> getAllPost() {

        // Sort sort = Sort.by(Sort.Direction.DESC, "id", "createDate");
        List<Post> entity = repository.findAll();

        return entity.stream().map(PostResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<PostResponseDTO> getPost(long postId) {
        Optional<Post> entity = repository.findById(postId);

        return entity.stream().map(PostResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public Long createPost(PostRequestDTO postDTO) {

        return repository.save(postDTO.toEntity()).getPostId();
    }

    @Override
    public Long updatePost(Long postId, PostRequestDTO postRequestDTO) {
        Post entity = repository.findById(postId).orElseThrow(null);

        entity.update(postRequestDTO.getPostTitle(), postRequestDTO.getPostDescription());

        return postId;
    }

    @Override
    public void deletePost(long postId) {
        repository.deleteById(postId);
    }


}
