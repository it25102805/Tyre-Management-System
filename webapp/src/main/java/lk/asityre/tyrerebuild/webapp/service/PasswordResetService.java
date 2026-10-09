package lk.asityre.tyrerebuild.webapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Handles the "forgot password" flow:
 *   1. requestReset(email)  -> creates a one-time token and emails a reset link
 *   2. isValid(token)       -> checks the link when the user opens it
 *   3. resetPassword(...)   -> saves the new password and burns the token
 *
 * Tokens are kept in memory (they are lost if the app restarts, which is fine
 * because they only live for 15 minutes anyway).
 */
@Service
public class PasswordResetService {

    private static final Logger log = LoggerFactory.getLogger(PasswordResetService.class);
    private static final long EXPIRY_MINUTES = 15;

    private record TokenInfo(String email, Instant expiresAt) {}

    private final Map<String, TokenInfo> tokens = new ConcurrentHashMap<>();
    private final SecureRandom random = new SecureRandom();

    private final UserService userService;
    private final ObjectProvider<JavaMailSender> mailSenderProvider;

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    @Value("${spring.mail.username:}")
    private String fromAddress;

    public PasswordResetService(UserService userService,
                                ObjectProvider<JavaMailSender> mailSenderProvider) {
        this.userService = userService;
        this.mailSenderProvider = mailSenderProvider;
    }

    /** Step 1: user typed their email on the forgot-password page. */
    public void requestReset(String email) {
        if (email == null || email.isBlank()) return;
        String clean = email.trim();

        // Do nothing (silently) if the email is not registered, so nobody can
        // use this page to find out which emails exist in the system.
        if (!userService.emailExists(clean)) return;

        // Remove any older links for this email
        tokens.values().removeIf(t -> t.email().equalsIgnoreCase(clean));

        String token = generateToken();
        tokens.put(token, new TokenInfo(clean, Instant.now().plus(EXPIRY_MINUTES, ChronoUnit.MINUTES)));

        sendEmail(clean, baseUrl + "/reset-password?token=" + token);
    }

    /** Step 2: is the link in the email still good? */
    public boolean isValid(String token) {
        purgeExpired();
        return token != null && tokens.containsKey(token);
    }

    /** Step 3: user submitted the new password. */
    public void resetPassword(String token, String newPassword) {
        purgeExpired();
        TokenInfo info = (token == null) ? null : tokens.get(token);
        if (info == null) {
            throw new IllegalArgumentException("This reset link is invalid or has expired.");
        }
        userService.updatePassword(info.email(), newPassword);
        tokens.remove(token); // one-time use
    }

    // ---------------------------------------------------------------

    private String generateToken() {
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private void purgeExpired() {
        Instant now = Instant.now();
        tokens.values().removeIf(t -> t.expiresAt().isBefore(now));
    }

    private void sendEmail(String to, String link) {
        JavaMailSender sender = mailSenderProvider.getIfAvailable();

        // Development mode: no mail configured -> print the link in the console
        if (sender == null || fromAddress.isBlank()) {
            log.warn("Mail is not configured. Password reset link for {}: {}", to, link);
            return;
        }

        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom(fromAddress);
            msg.setTo(to);
            msg.setSubject("ASI Tyre - Reset your password");
            msg.setText("Hello,\n\n"
                    + "We received a request to reset your ASI Tyre password.\n"
                    + "Click the link below to choose a new password "
                    + "(valid for " + EXPIRY_MINUTES + " minutes):\n\n"
                    + link + "\n\n"
                    + "If you did not request this, you can ignore this email.\n\n"
                    + "ASI Tyre");
            sender.send(msg);
        } catch (MailException e) {
            log.error("Could not send reset email to {}: {}", to, e.getMessage());
            log.warn("Reset link (fallback) for {}: {}", to, link);
        }
    }
}