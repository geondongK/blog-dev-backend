package blogservices.blogdevbackend.domain.user.repository;

import blogservices.blogdevbackend.domain.user.domain.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    boolean existsByEmail(String email); // 이메일 중복 가입 방지

    boolean existsByName(String name); // 별명 중복 가입 방지

    User findByProviderId(Long providerId); // OAuth providerId 회원 조회
}
