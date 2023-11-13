package blogservices.blogdevbackend.domain.like.repository;

import blogservices.blogdevbackend.domain.like.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {
    List<Like> findByPostId(int postId); // 라이크 조회

    Like findByCommentIdAndUserId(int commentId, long userId); // 라이크 조회 후 삭제
}
