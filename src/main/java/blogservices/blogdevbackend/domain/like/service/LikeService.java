package blogservices.blogdevbackend.domain.like.service;

import blogservices.blogdevbackend.domain.like.dto.LikeRequestDto;
import blogservices.blogdevbackend.domain.like.dto.LikeResponseDto;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface LikeService {

    List<LikeResponseDto> getLike(int postId);

    List<LikeResponseDto> addLike(LikeRequestDto request);

    void deleteLike(int commentId, long userId);
}
