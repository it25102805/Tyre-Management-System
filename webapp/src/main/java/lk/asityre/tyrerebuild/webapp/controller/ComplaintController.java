package lk.asityre.tyrerebuild.webapp.controller;

import lk.asityre.tyrerebuild.webapp.model.Complaint;
import lk.asityre.tyrerebuild.webapp.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @GetMapping
    public List<Complaint> getAll() {
        return complaintService.getAllComplaints();
    }

    @GetMapping("/{id}")
    public Complaint getById(@PathVariable Integer id) {
        return complaintService.getComplaintById(id);
    }

    @PostMapping
    public Complaint create(@RequestBody Complaint complaint) {
        return complaintService.saveComplaint(complaint);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        complaintService.deleteComplaint(id);
    }

    @PutMapping("/{id}/status")
    public Complaint updateStatus(@PathVariable Integer id, @RequestParam String status) {
        return complaintService.updateComplaintStatus(id, status);
    }

    @PutMapping("/{id}")
    public Complaint update(@PathVariable Integer id, @RequestBody Complaint complaintDetails) {
        return complaintService.updateComplaint(id, complaintDetails);
    }
}