package lk.asityre.tyrerebuild.webapp.service;

<<<<<<< HEAD
import lk.asityre.tyrerebuild.webapp.model.User;
import lk.asityre.tyrerebuild.webapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
=======
import lk.asityre.tyrerebuild.webapp.model.User;                 // was com.tirerebuild.model.User
import lk.asityre.tyrerebuild.webapp.repository.UserRepository;  // was com.tirerebuild.repository.UserRepository
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
>>>>>>> origin/main

@Service
public class UserService {

<<<<<<< HEAD
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User saveUser(User user) {
        if (user.getRole() == null) user.setRole("CUSTOMER");
        if (user.getApprovalStatus() == null) user.setApprovalStatus("PENDING");
        if (user.getStatus() == null) user.setStatus("ACTIVE");
        return userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public User updateUser(Integer id, User updatedDetails) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setFirstName(updatedDetails.getFirstName());
            existingUser.setLastName(updatedDetails.getLastName());
            existingUser.setContactNumber(updatedDetails.getContactNumber());
            existingUser.setEmail(updatedDetails.getEmail());
            existingUser.setRole(updatedDetails.getRole());
            existingUser.setApprovalStatus(updatedDetails.getApprovalStatus());
            existingUser.setStatus(updatedDetails.getStatus());
            return userRepository.save(existingUser);
        }
        return null;
    }
}
=======
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
>>>>>>> origin/main
