package blogservices.blogdevbackend.domain.post.repository;

import blogservices.blogdevbackend.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
