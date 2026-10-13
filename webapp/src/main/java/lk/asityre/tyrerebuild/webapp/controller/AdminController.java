package lk.asityre.tyrerebuild.webapp.controller;

import lk.asityre.tyrerebuild.webapp.model.Appointment;
import lk.asityre.tyrerebuild.webapp.model.Staff;
import lk.asityre.tyrerebuild.webapp.model.User;
import lk.asityre.tyrerebuild.webapp.service.AppointmentService;
import lk.asityre.tyrerebuild.webapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final AppointmentService appointmentService;

    public AdminController(UserService userService, AppointmentService appointmentService) {
        this.userService = userService;
        this.appointmentService = appointmentService;
    }

    @GetMapping("/registrations")
    public String registrations(Model model) {
        Map<Integer, User> userMap = userService.getUserMap();
        List<User> customers = userService.getCustomers();
        List<Staff> staffList = userService.getStaffList();

        Map<Integer, Appointment> appointmentMap = new HashMap<>();
        for (Appointment a : appointmentService.getAll()) {
            appointmentMap.put(a.getUserId(), a);
        }

        long pendingCustomers = customers.stream()
                .filter(c -> "PENDING".equals(c.getApprovalStatus())).count();
        long pendingStaff = staffList.stream()
                .filter(s -> {
                    User u = userMap.get(s.getUserId());
                    return u != null && "PENDING".equals(u.getApprovalStatus());
                }).count();

        model.addAttribute("customers", customers);
        model.addAttribute("staffList", staffList);
        model.addAttribute("userMap", userMap);
        model.addAttribute("appointmentMap", appointmentMap);
        model.addAttribute("positions", UserService.STAFF_POSITIONS);
        model.addAttribute("pendingCustomers", pendingCustomers);
        model.addAttribute("pendingStaff", pendingStaff);
        return "admin-registrations";
    }

    @PostMapping("/registrations/customer")
    public String decideCustomer(@RequestParam Integer userId,
                                 @RequestParam String decision,
                                 RedirectAttributes redirect) {
        try {
            boolean approve = "approve".equals(decision);
            userService.decideCustomer(userId, approve);
            redirect.addFlashAttribute("success",
                    "Customer " + (approve ? "approved." : "rejected."));
        } catch (IllegalArgumentException e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/registrations";
    }

    @PostMapping("/registrations/staff")
    public String decideStaff(@RequestParam Integer userId,
                              @RequestParam String decision,
                              @RequestParam(required = false) String position,
                              RedirectAttributes redirect) {
        try {
            boolean approve = "approve".equals(decision);
            userService.decideStaff(userId, approve, position);
            redirect.addFlashAttribute("success", "Staff member " + (approve ? "approved." : "rejected."));
        } catch (IllegalArgumentException e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/registrations";
    }
}