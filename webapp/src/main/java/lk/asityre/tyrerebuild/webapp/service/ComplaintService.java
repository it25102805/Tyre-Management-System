package lk.asityre.tyrerebuild.webapp.service;

import lk.asityre.tyrerebuild.webapp.model.Complaint;
import lk.asityre.tyrerebuild.webapp.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    public Complaint getComplaintById(Integer id) {
        return complaintRepository.findById(id).orElse(null);
    }

    public void deleteComplaint(Integer id) {
        complaintRepository.deleteById(id);
    }

    public Complaint updateComplaintStatus(Integer id, String newStatus) {
        Complaint existingComplaint = complaintRepository.findById(id).orElse(null);
        if (existingComplaint != null) {
            existingComplaint.setStatus(newStatus);
            return complaintRepository.save(existingComplaint);
        }
        return null;
    }

    public Complaint updateComplaint(Integer id, Complaint updatedDetails) {
        Complaint existingComplaint = complaintRepository.findById(id).orElse(null);
        if (existingComplaint != null) {
            existingComplaint.setDescription(updatedDetails.getDescription());
            existingComplaint.setStatus(updatedDetails.getStatus());
            return complaintRepository.save(existingComplaint);
        }
        return null;
    }

    public Complaint saveComplaint(Complaint complaint) {
        if (complaint.getDescription() == null || complaint.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Complaint description cannot be empty");
        }
        if (complaint.getCustomerId() == null) {
            throw new IllegalArgumentException("Customer ID is required");
        }
        if (complaint.getOrderId() == null) {
            throw new IllegalArgumentException("Order ID is required");
        }
        return complaintRepository.save(complaint);
    }
}