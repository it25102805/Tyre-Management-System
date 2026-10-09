package lk.asityre.tyrerebuild.webapp.controller;

import lk.asityre.tyrerebuild.webapp.service.PasswordResetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PasswordResetController {

    private final PasswordResetService resetService;

    public PasswordResetController(PasswordResetService resetService) {
        this.resetService = resetService;
    }

    // ---------- Step 1: ask for the email ----------
    @GetMapping("/forgot-password")
    public String forgotPage() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotSubmit(@RequestParam String email, Model model) {
        resetService.requestReset(email);
        // Same message whether or not the email exists
        model.addAttribute("message",
                "If that email is registered, a password reset link has been sent. "
                        + "Please check your inbox.");
        return "forgot-password";
    }

    // ---------- Step 2: open the link from the email ----------
    @GetMapping("/reset-password")
    public String resetPage(@RequestParam(required = false) String token, Model model) {
        if (!resetService.isValid(token)) {
            model.addAttribute("invalid", true);
            return "reset-password";
        }
        model.addAttribute("token", token);
        return "reset-password";
    }

    // ---------- Step 3: save the new password ----------
    @PostMapping("/reset-password")
    public String resetSubmit(@RequestParam String token,
                              @RequestParam String password,
                              @RequestParam String confirmPassword,
                              Model model) {
        if (password.length() < 6) {
            model.addAttribute("token", token);
            model.addAttribute("error", "Password must be at least 6 characters.");
            return "reset-password";
        }
        if (!password.equals(confirmPassword)) {
            model.addAttribute("token", token);
            model.addAttribute("error", "Passwords do not match.");
            return "reset-password";
        }

        try {
            resetService.resetPassword(token, password);
        } catch (IllegalArgumentException e) {
            model.addAttribute("invalid", true);
            return "reset-password";
        }
        return "redirect:/login?reset";
    }
}