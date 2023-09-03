package az.project.bankdemoboot.repository;

import az.project.bankdemoboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUsernameAndPasswordAndActive(String username, String password, Integer active);
}
