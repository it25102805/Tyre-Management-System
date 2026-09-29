package lk.asityre.tyrerebuild.webapp.service;

import lk.asityre.tyrerebuild.webapp.model.User;                 // was com.tirerebuild.model.User
import lk.asityre.tyrerebuild.webapp.repository.UserRepository;  // was com.tirerebuild.repository.UserRepository
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
}
