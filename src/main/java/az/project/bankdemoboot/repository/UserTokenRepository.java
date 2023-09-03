package az.project.bankdemoboot.repository;

import az.project.bankdemoboot.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
}
