package kz.balaguide.core.repositories.auth;

import kz.balaguide.core.entities.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {

    Optional<AuthUser> findByPhoneNumber(String phoneNumber);
}
