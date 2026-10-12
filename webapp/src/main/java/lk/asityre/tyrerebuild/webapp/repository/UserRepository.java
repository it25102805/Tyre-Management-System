package lk.asityre.tyrerebuild.webapp.repository;

import lk.asityre.tyrerebuild.webapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    // UPDATE: Only allow login if the user is APPROVED and ACTIVE
    Optional<User> findByEmailAndPasswordAndApprovalStatusAndStatus(
            String email,
            String password,
            String approvalStatus,
            String status
    );

    boolean existsByEmail(String email);
}