package lk.asityre.tyrerebuild.webapp.service;

import lk.asityre.tyrerebuild.webapp.model.Complaint;
import lk.asityre.tyrerebuild.webapp.repository.ComplaintRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ComplaintService {

    private final ComplaintRepository complaintRepository;

    public ComplaintService(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAllByOrderByComplaintDateDesc();
    }

    public List<Complaint> getComplaintsByCustomer(Integer customerId) {
        return complaintRepository.findByCustomerIdOrderByComplaintDateDesc(customerId);
    }

    public Complaint submitComplaint(Integer orderId, Integer customerId, String description) {
        Complaint c = new Complaint();
        c.setOrderId(orderId);
        c.setCustomerId(customerId);
        c.setDescription(description);
        c.setComplaintDate(LocalDate.now());
        c.setStatus("OPEN");
        return complaintRepository.save(c);
    }

    public Complaint updateStatus(Integer complaintId, String status) {
        Complaint c = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new IllegalArgumentException("Complaint not found with ID: " + complaintId));
        c.setStatus(status);
        return complaintRepository.save(c);
    }
}