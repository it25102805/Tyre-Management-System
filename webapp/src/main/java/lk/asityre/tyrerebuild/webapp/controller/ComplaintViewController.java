package lk.asityre.tyrerebuild.webapp.controller;

import lk.asityre.tyrerebuild.webapp.model.Complaint;
import lk.asityre.tyrerebuild.webapp.service.ComplaintService;
import lk.asityre.tyrerebuild.webapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class ComplaintViewController {

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private OrderService orderService; // For order dropdown

    @GetMapping("/complaints")
    public String viewComplaints(Model model) {
        model.addAttribute("complaints", complaintService.getAllComplaints());
        model.addAttribute("orders", orderService.getAllOrders()); // Populate order dropdown
        model.addAttribute("newComplaint", new Complaint());
        return "complaints"; // Refers to complaints.html in templates folder
    }

    @PostMapping("/complaints")
    public String addComplaint(@ModelAttribute Complaint newComplaint) {
        complaintService.saveComplaint(newComplaint);
        return "redirect:/complaints";
    }
}