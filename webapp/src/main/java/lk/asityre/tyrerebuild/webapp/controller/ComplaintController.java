package lk.asityre.tyrerebuild.webapp.controller;

import jakarta.servlet.http.HttpSession;
import lk.asityre.tyrerebuild.webapp.service.ComplaintService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/complaints")
public class ComplaintController {

    private final ComplaintService complaintService;

    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @GetMapping
    public String viewComplaints(HttpSession session, Model model) {
        String role = (String) session.getAttribute("role");
        Integer userId = (Integer) session.getAttribute("userId");

        if (role == null || userId == null) {
            return "redirect:/login";
        }

        if ("ADMIN".equals(role) || "MANAGER".equals(role) || "EMPLOYEE".equals(role)) {
            model.addAttribute("complaints", complaintService.getAllComplaints());
        } else {
            model.addAttribute("complaints", complaintService.getComplaintsByCustomer(userId));
        }
        return "complaints";
    }

    @PostMapping("/submit")
    public String submitComplaint(@RequestParam Integer orderId, @RequestParam String description, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        complaintService.submitComplaint(orderId, userId, description);
        return "redirect:/complaints";
    }
    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable Integer id, @RequestParam String status, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if ("ADMIN".equals(role) || "MANAGER".equals(role)) {
            complaintService.updateStatus(id, status);
        }
        return "redirect:/complaints";
    }
}