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
        return userRepository.findByEmailAndPasswordAndApprovalStatusAndStatus(
                email, password, "APPROVED", "ACTIVE"
        );
    }

    /** Creates a CUSTOMER and books their appointment atomically. */
    @Transactional
    public void registerVisitor(String firstName, String lastName, String email, String password,
                                String date, String time, String purpose) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("That email is already registered.");
        }

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName == null || lastName.isBlank() ? "-" : lastName);
        user.setUsername(email);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole("CUSTOMER");
        user.setApprovalStatus("PENDING");
        user.setStatus("ACTIVE");

        userRepository.save(user);
        appointmentService.book(user.getUserId(), LocalDate.parse(date), LocalTime.parse(time), purpose);
    }

    /** Mapping of user_id -> User for view lookups. */
    public Map<Integer, User> getUserMap() {
        Map<Integer, User> map = new HashMap<>();
        for (User u : userRepository.findAll()) {
            map.put(u.getUserId(), u);
        }
        return map;
    }
}