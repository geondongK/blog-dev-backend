package blogservices.blogdevbackend.domain.user.repository;

import blogservices.blogdevbackend.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email); // 유저 찾기

    boolean existsByEmail(String email); // 이메일 중복 가입 방지

    boolean existsByName(String name); // 별명 중복 가입 방지

    User findByProviderId(Long providerId); // OAuth providerId 회원 조회
}
