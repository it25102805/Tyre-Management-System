package lk.asityre.tyrerebuild.webapp.controller;

import jakarta.servlet.http.HttpSession;
import lk.asityre.tyrerebuild.webapp.model.User;
import lk.asityre.tyrerebuild.webapp.security.AccessPolicy;
import lk.asityre.tyrerebuild.webapp.service.AppointmentService;
import lk.asityre.tyrerebuild.webapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.Optional;

@Controller
public class MainController {

    private final UserService userService;
    private final AppointmentService appointmentService;

    public MainController(UserService userService, AppointmentService appointmentService) {
        this.userService = userService;
        this.appointmentService = appointmentService;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    // ---------- Login ----------
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password,
                        HttpSession session, Model model) {

        Optional<User> found = userService.findByCredentials(email.trim(), password);

        if (found.isEmpty()) {
            model.addAttribute("error", "Email or password is incorrect.");
            return "login";
        }

        User user = found.get();

        if ("PENDING".equals(user.getApprovalStatus())) {
            model.addAttribute("error", "Your registration is still waiting for admin approval.");
            return "login";
        }
        if (!"APPROVED".equals(user.getApprovalStatus())) {
            model.addAttribute("error", "Your registration was not approved. Please contact the company.");
            return "login";
        }
        if (!"ACTIVE".equals(user.getStatus())) {
            model.addAttribute("error", "Your account is inactive.");
            return "login";
        }

        String accessRole = userService.accessRoleOf(user);

        session.setAttribute("userId", user.getUserId());
        session.setAttribute("name", user.getName());
        session.setAttribute("role", user.getRole());
        session.setAttribute("accessRole", accessRole);
        session.setAttribute("pages", new HashSet<>(AccessPolicy.pagesFor(accessRole)));
        return "redirect:/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    // ---------- Register (one page with a Customer / Staff switch) ----------

    @GetMapping("/register")
    public String registerPage(@RequestParam(defaultValue = "customer") String tab, Model model) {
        model.addAttribute("tab", tab);
        return "register";
    }

    // Old separate pages now point to the single page
    @GetMapping("/register/customer")
    public String registerCustomerRedirect() {
        return "redirect:/register";
    }

    @GetMapping("/register/staff")
    public String registerStaffRedirect() {
        return "redirect:/register?tab=staff";
    }

    @PostMapping("/register/customer")
    public String registerCustomer(@RequestParam String firstName, @RequestParam String lastName,
                                   @RequestParam String email, @RequestParam String phone,
                                   @RequestParam String password, @RequestParam String date,
                                   @RequestParam String time, @RequestParam String purpose,
                                   Model model, RedirectAttributes redirect) {
        try {
            userService.registerCustomer(firstName.trim(), lastName.trim(), email.trim(),
                    phone.trim(), password, date, time, purpose.trim());
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("tab", "customer");
            return "register";
        }
        redirect.addFlashAttribute("success",
                "Registration and meeting request sent. You can log in once the admin approves you.");
        return "redirect:/login";
    }

    @PostMapping("/register/staff")
    public String registerStaff(@RequestParam String firstName, @RequestParam String lastName,
                                @RequestParam String email, @RequestParam String phone,
                                @RequestParam String password,
                                Model model, RedirectAttributes redirect) {
        try {
            userService.registerStaff(firstName.trim(), lastName.trim(), email.trim(), phone.trim(), password);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("tab", "staff");
            return "register";
        }
        redirect.addFlashAttribute("success",
                "Application submitted. You can log in once the admin approves you.");
        return "redirect:/login";
    }

    // ---------- Dashboard ----------
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        String role = (String) session.getAttribute("role");
        if (role == null) return "redirect:/login";

        boolean staff = !"CUSTOMER".equals(role);

        if (staff) {
            model.addAttribute("appointments", appointmentService.getAll());
        } else {
            model.addAttribute("appointments",
                    appointmentService.getForUser((Integer) session.getAttribute("userId")));
        }

        model.addAttribute("name", session.getAttribute("name"));
        model.addAttribute("role", role);
        model.addAttribute("accessRole", session.getAttribute("accessRole"));
        model.addAttribute("pages", session.getAttribute("pages"));
        model.addAttribute("staff", staff);
        model.addAttribute("userMap", userService.getUserMap());
        return "dashboard";
    }

    // Staff (not customers) can change an appointment status
    @PostMapping("/status")
    public String updateStatus(@RequestParam Integer id, @RequestParam String status, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (role != null && !"CUSTOMER".equals(role)) {
            appointmentService.updateStatus(id, status);
        }
        return "redirect:/dashboard";
    }
}