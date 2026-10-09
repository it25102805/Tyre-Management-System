package lk.asityre.tyrerebuild.webapp.controller;   // was com.tirerebuild.controller

import lk.asityre.tyrerebuild.webapp.model.User;                      // was com.tirerebuild.model.User
import lk.asityre.tyrerebuild.webapp.service.AppointmentService;      // was com.tirerebuild.service.AppointmentService
import lk.asityre.tyrerebuild.webapp.service.UserService;             // was com.tirerebuild.service.UserService
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
        return "redirect:/login";
    }

    // ---------- Login ----------
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password,
                        HttpSession session, Model model) {
        Optional<User> found = userService.login(email, password);

        if (found.isEmpty()) {
            model.addAttribute("error", "Email or password is incorrect.");
            return "login";
        }
        User user = found.get();
        session.setAttribute("userId", user.getUserId());
        session.setAttribute("name", user.getName());
        session.setAttribute("role", user.getRole());
        return "redirect:/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    // ---------- Register (visitor books a meeting) ----------
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String name, @RequestParam String email,
                           @RequestParam String password, @RequestParam String date,
                           @RequestParam String time, @RequestParam String purpose,
                           Model model) {
        try {
            userService.registerVisitor(name, email, password, date, time, purpose);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
        return "redirect:/login";
    }

    // ---------- Dashboard ----------
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        String role = (String) session.getAttribute("role");
        if (role == null) return "redirect:/login";

        boolean staff = role.equals("EMPLOYEE") || role.equals("ADMIN");

        if (staff) {
            model.addAttribute("appointments", appointmentService.getAll());
        } else {
            model.addAttribute("appointments",
                    appointmentService.getForUser((Integer) session.getAttribute("userId")));
        }

        model.addAttribute("name", session.getAttribute("name"));
        model.addAttribute("role", role);
        model.addAttribute("staff", staff);
        model.addAttribute("userMap", userService.getUserMap());
        return "dashboard";
    }

    // Employees and admins can change an appointment status
    @PostMapping("/status")
    public String updateStatus(@RequestParam Integer id, @RequestParam String status, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if ("EMPLOYEE".equals(role) || "ADMIN".equals(role)) {
            appointmentService.updateStatus(id, status);
        }
        return "redirect:/dashboard";
    }
}
