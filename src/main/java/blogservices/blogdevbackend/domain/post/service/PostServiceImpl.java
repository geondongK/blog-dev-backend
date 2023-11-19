package blogservices.blogdevbackend.domain.post.service;

import blogservices.blogdevbackend.domain.post.domain.Post;
import blogservices.blogdevbackend.domain.post.dto.PostRequestDto;
import blogservices.blogdevbackend.domain.post.dto.PostResponseDto;
import blogservices.blogdevbackend.domain.post.repository.PostRepository;
import blogservices.blogdevbackend.global.common.S3Utils;
import blogservices.blogdevbackend.global.exception.GlobalException;
import blogservices.blogdevbackend.global.exception.GlobalExceptionResponseCode;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

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
        try {
            if (request.getTitle().length() > 50) {
                throw new GlobalException(GlobalExceptionResponseCode.TITLE_LENGTH_ERROR);
            }

            // 게시물 저장
            Post entity = repository.save(request.toEntity());

            return entity.getPostId();
        } catch (Exception e) {
            log.error("Exception::Err_Msg = {}", e.getStackTrace()[0]);
            throw new RuntimeException();
        }
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
            // 파일 이름 변경
            String originalFilename = S3Utils.buildFileName(file);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());
            
            amazonS3.putObject(bucket, originalFilename, file.getInputStream(), metadata);
            return amazonS3.getUrl(bucket, originalFilename).toString();
        } catch (Exception e) {
            log.error("Exception::Err_Msg = {}", e.getStackTrace()[0]);
            throw new GlobalException(GlobalExceptionResponseCode.DATABASE_ERROR);
        }
    }
}
