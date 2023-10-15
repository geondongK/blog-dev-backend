package blogservices.blogdevbackend.domain.post.service;

import blogservices.blogdevbackend.domain.post.domain.Post;
import blogservices.blogdevbackend.domain.post.dto.PostRequestDto;
import blogservices.blogdevbackend.domain.post.dto.PostResponseDto;
import blogservices.blogdevbackend.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository repository;

    @Override
    public List<PostResponseDto> getAllPost(int limit, int offset) {
        // limit != 0;
        int page = offset / limit;

        log.info("limit = {}, offset = {}", limit, page);

        // Sort sort = Sort.by(Sort.Direction.DESC, "id", "createDate");
        List<Post> entity = repository.findSliceBy(PageRequest.of(page, limit));

        return entity.stream().map(PostResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public List<PostResponseDto> getPost(long postId) {
        Optional<Post> entity = repository.findById(postId);

        return entity.stream().map(PostResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public Long createPost(PostRequestDto request) {

        log.info("createPost:: wrtierId = {}, writer = {}, description = {}, title = {} "
                , request.getWriterId(), request.getWriter(), request.getDescription(), request.getTitle());

        Post entity = repository.save(request.toEntity());
        return entity.getPostId();
    }

    @Override
    public Long updatePost(Long postId, PostRequestDto request) {
        Post entity = repository.findById(postId).orElseThrow(null);

        entity.update(request.getTitle(), request.getDescription());

        return postId;
    }

    @Override
    public void deletePost(long postId) {
        repository.deleteById(postId);
    }

    @Override
    public List<PostResponseDto> searchPost(String keyword, int limit, int offset) {

        int page = offset / limit;

        log.info("aa limit = {}, aa offset = {}, aa keyword = {}", limit, page, keyword);

        List<Post> entity = repository.findByTitleContaining(keyword, PageRequest.of(page, limit));

        return entity.stream().map(PostResponseDto::new).collect(Collectors.toList());

    }
}
