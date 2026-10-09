package lk.asityre.tyrerebuild.webapp.service;

import lk.asityre.tyrerebuild.webapp.model.User;
import lk.asityre.tyrerebuild.webapp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AppointmentService appointmentService;

    public UserService(UserRepository userRepository, AppointmentService appointmentService) {
        this.userRepository = userRepository;
        this.appointmentService = appointmentService;
    }

    public Optional<User> login(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    /** Creates a VISITOR and books their meeting. Both succeed or both fail. */
    @Transactional
    public void registerVisitor(String name, String email, String password,
                                String date, String time, String purpose) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("That email is already registered.");
        }

        User user = new User();
        user.setFirstName(name);
        user.setLastName("-");
        user.setUsername(email);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole("CUSTOMER");
        userRepository.save(user);
        appointmentService.book(user.getUserId(), LocalDate.parse(date), LocalTime.parse(time), purpose);
    }

    /** user_id -> User, so the dashboard can show names next to appointments. */
    public Map<Integer, User> getUserMap() {
        Map<Integer, User> map = new HashMap<>();
        for (User u : userRepository.findAll()) {
            map.put(u.getUserId(), u);
        }
        return map;
    }

    // ---------- Forgot password ----------

    /** Used by the forgot-password page to check the email is registered. */
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    /** Saves a new password for the user with this email. */
    @Transactional
    public void updatePassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        // Saved as plain text, the same way login() checks it
        // (findByEmailAndPassword compares the raw password).
        user.setPassword(newPassword);
        userRepository.save(user);
    }
}