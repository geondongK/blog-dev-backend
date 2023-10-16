package blogservices.blogdevbackend.domain.like.service;

import blogservices.blogdevbackend.domain.like.domain.Like;
import blogservices.blogdevbackend.domain.like.dto.LikeRequestDto;
import blogservices.blogdevbackend.domain.like.dto.LikeResponseDto;
import blogservices.blogdevbackend.domain.like.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class LikeServiceImpl implements LikeService {

    private final LikeRepository repository;

    /* 좋아요 조회 */
    @Override
    public List<LikeResponseDto> getLike(int postId) {
        List<Like> entity = repository.findByPostId(postId);

        return entity.stream().map(LikeResponseDto::new).collect(Collectors.toList());
    }

    /* 좋아요 추가 */
    @Override
    public List<LikeResponseDto> addLike(LikeRequestDto request) {

        List<Like> entity = Collections.singletonList(repository.save(request.toEntity()));
        return entity.stream().map(LikeResponseDto::new).collect(Collectors.toList());
    }

    /* 좋아요 삭제 */
    @Override
    public void deleteLike(int commentId, long userId) {

        log.info("commentId = {}, userId = {}", commentId, userId);

        Like entity = repository.findByCommentIdAndUserId(commentId, userId);

        repository.deleteById(entity.getLikeId());
    }
}
