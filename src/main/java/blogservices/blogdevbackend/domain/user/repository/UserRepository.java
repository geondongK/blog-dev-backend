package blogservices.blogdevbackend.domain.user.repository;

import blogservices.blogdevbackend.domain.user.domain.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email); // 중복 가입 방지
}
