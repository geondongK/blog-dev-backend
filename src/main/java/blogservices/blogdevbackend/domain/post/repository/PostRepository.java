package blogservices.blogdevbackend.domain.post.repository;

import blogservices.blogdevbackend.domain.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 포스트 페이징 기능
    @Query("SELECT p FROM Post p ORDER BY p.postId DESC")
    List<Post> findSliceBy(Pageable pageable);

    // 포스트 검색 기능
    List<Post> findByTitleContaining(String Keyword, Pageable pageable);
}
