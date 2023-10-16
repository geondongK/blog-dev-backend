package blogservices.blogdevbackend.domain.like.repository;

import blogservices.blogdevbackend.domain.like.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Integer> {
    List<Like> findByPostId(int postId); // 라이크 조회

    Like findByCommentIdAndUserId(int commentId, long userId); // 라이크 조회 후 삭제
}
