package kz.balaguide.auth_module.repository;

import kz.balaguide.common_module.core.entities.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {

    Optional<AuthUser> findByPhoneNumber(String phoneNumber);
}
