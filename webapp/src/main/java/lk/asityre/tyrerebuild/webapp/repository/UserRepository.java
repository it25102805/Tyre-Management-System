package lk.asityre.tyrerebuild.webapp.repository;

import lk.asityre.tyrerebuild.webapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmailAndPasswordAndApprovalStatusAndStatus(String email, String password, String approvalStatus, String status);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}