package blogservices.blogdevbackend.domain.post.dao;

import blogservices.blogdevbackend.domain.post.domain.Post;

import java.util.List;
import java.util.Optional;

public interface PostDAO {
    List<Post> getAllPost();

    Optional<Post> getPost(long postId);

    Post savePost(Post post);

    Post updatePost(Post post);

    void deletePost(long postId);

}
