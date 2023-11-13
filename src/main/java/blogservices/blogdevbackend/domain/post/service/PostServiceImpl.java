package blogservices.blogdevbackend.domain.post.service;

import blogservices.blogdevbackend.domain.post.domain.Post;
import blogservices.blogdevbackend.domain.post.dto.PostRequestDto;
import blogservices.blogdevbackend.domain.post.dto.PostResponseDto;
import blogservices.blogdevbackend.domain.post.repository.PostRepository;
import blogservices.blogdevbackend.global.common.S3Utils;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository repository;
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public List<PostResponseDto> getAllPost(int limit, int offset) {
        // limit != 0;
        int page = offset / limit;

        // 게시물 검색
        List<Post> entity = repository.findSliceBy(PageRequest.of(page, limit));

        return entity.stream().map(PostResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public List<PostResponseDto> getPost(long postId) {
        Optional<Post> entity = repository.findById(postId);

        // 조회 수 증가
        entity.get().increaseHits();

        return entity.stream().map(PostResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public Long createPost(PostRequestDto request) {
        // 게시물 저장
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

    public String uploadFile(MultipartFile file) {
        try {

            log.info("file = {}", file.getOriginalFilename());

            //String originalFilename = file.getOriginalFilename();

            String originalFilename = S3Utils.buildFileName(file.getOriginalFilename());


            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());


            amazonS3.putObject(bucket, originalFilename, file.getInputStream(), metadata);
            // return amazonS3.getUrl(bucket, originalFilename).toString();
            return null;
        } catch (Exception e) {
            log.error("Exception::Err_Msg = {}", e.getStackTrace()[0]);
            return "실패";
        }
    }
}
