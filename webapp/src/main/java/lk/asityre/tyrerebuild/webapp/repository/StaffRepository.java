package lk.asityre.tyrerebuild.webapp.repository;

import lk.asityre.tyrerebuild.webapp.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Integer> {

    Optional<Staff> findByUserId(Integer userId);
}