package lk.asityre.tyrerebuild.webapp.repository;

import lk.asityre.tyrerebuild.webapp.model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {

    List<Complaint> findByCustomerIdOrderByComplaintDateDesc(Integer customerId);

    List<Complaint> findByStatusOrderByComplaintDateDesc(String status);

    List<Complaint> findAllByOrderByComplaintDateDesc();
}