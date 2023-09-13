package blogservices.blogdevbackend.domain.post.dao;

import blogservices.blogdevbackend.domain.post.dao.PostDAO;
import blogservices.blogdevbackend.domain.post.domain.Post;
import blogservices.blogdevbackend.domain.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostDAOImpl implements PostDAO {

    PostRepository repository;

    @Autowired
    public PostDAOImpl(PostRepository postRepository) {
        this.repository = postRepository;
    }

    @Override
    public List<Post> getAllPost() {
        return repository.findAll();
    }

    @Override
    public Optional<Post> getPost(long postId) {
        Optional<Post> getPost = repository.findById(postId);
        return getPost;
    }

    @Override
    public Post savePost(Post post) {
        Post createPost = repository.save(post);
        return createPost;
    }

    @Override
    public Post updatePost(Post post) {
        return repository.save(post);
    }

    @Override
    public void deletePost(long postId) {
        repository.deleteById(postId);
    }
}
