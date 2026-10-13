package lk.asityre.tyrerebuild.webapp.service;

import lk.asityre.tyrerebuild.webapp.model.Staff;
import lk.asityre.tyrerebuild.webapp.model.User;
import lk.asityre.tyrerebuild.webapp.repository.StaffRepository;
import lk.asityre.tyrerebuild.webapp.repository.UserRepository;
import lk.asityre.tyrerebuild.webapp.security.AccessPolicy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    public static final List<String> STAFF_POSITIONS = List.of(
            "Purchasing Officer", "Warehouse Keeper", "Quality Checker", "Manager", "Employee");

    private final UserRepository userRepository;
    private final StaffRepository staffRepository;
    private final AppointmentService appointmentService;

    public UserService(UserRepository userRepository,
                       StaffRepository staffRepository,
                       AppointmentService appointmentService) {
        this.userRepository = userRepository;
        this.staffRepository = staffRepository;
        this.appointmentService = appointmentService;
    }

    // ---------- Login ----------

    /** Finds the user by email and password, whatever their approval status. */
    public Optional<User> findByCredentials(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(u -> u.getPassword() != null && u.getPassword().equals(password));
    }

    public String accessRoleOf(User user) {
        Staff staff = staffRepository.findByUserId(user.getUserId()).orElse(null);
        return AccessPolicy.accessRole(user, staff);
    }

    // ---------- Registration ----------

    /** Customer form: creates a PENDING customer and books their appointment. */
    @Transactional
    public void registerCustomer(String firstName, String lastName, String email, String phone,
                                 String password, String date, String time, String purpose) {
        checkEmailFree(email);
        checkPhone(phone);

        LocalDate meetingDate = LocalDate.parse(date);
        if (meetingDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("The meeting date cannot be in the past.");
        }

        User user = newUser(firstName, lastName, email, phone, password, "CUSTOMER");
        userRepository.save(user);
        appointmentService.book(user.getUserId(), meetingDate, LocalTime.parse(time), purpose);
    }

    /**
     * Staff form: creates a PENDING user with the position "Employee".
     * The admin chooses the real position when approving.
     */
    @Transactional
    public void registerStaff(String firstName, String lastName, String email,
                              String phone, String password) {
        checkEmailFree(email);
        checkPhone(phone);

        User user = newUser(firstName, lastName, email, phone, password, "EMPLOYEE");
        userRepository.save(user);

        Staff staff = new Staff();
        staff.setUserId(user.getUserId());
        staff.setRole("Employee");
        staffRepository.save(staff);
    }

    private void checkEmailFree(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("That email is already registered.");
        }
    }

    /** 10 digits, starting with 07. */
    private void checkPhone(String phone) {
        if (phone == null || !phone.matches("07\\d{8}")) {
            throw new IllegalArgumentException("Contact number must be 10 digits and start with 07.");
        }
    }

    private User newUser(String firstName, String lastName, String email, String phone,
                         String password, String role) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName == null || lastName.isBlank() ? "-" : lastName);
        user.setUsername(email);
        user.setEmail(email);
        user.setContactNumber(phone);
        user.setPassword(password);
        user.setRole(role);
        user.setApprovalStatus("PENDING");
        user.setStatus("ACTIVE");
        return user;
    }

    // ---------- Admin: lists ----------

    public Map<Integer, User> getUserMap() {
        Map<Integer, User> map = new HashMap<>();
        for (User u : userRepository.findAll()) {
            map.put(u.getUserId(), u);
        }
        return map;
    }

    /** Customers, pending ones first. */
    public List<User> getCustomers() {
        List<User> list = new ArrayList<>(userRepository.findByRoleOrderByUserIdDesc("CUSTOMER"));
        list.sort(Comparator.comparing((User u) -> !"PENDING".equals(u.getApprovalStatus())));
        return list;
    }

    /** Staff rows, pending ones first. */
    public List<Staff> getStaffList() {
        Map<Integer, User> users = getUserMap();
        List<Staff> list = new ArrayList<>(staffRepository.findAllByOrderByStaffIdDesc());
        list.sort(Comparator.comparing((Staff s) -> {
            User u = users.get(s.getUserId());
            return u == null || !"PENDING".equals(u.getApprovalStatus());
        }));
        return list;
    }

    // ---------- Admin: decisions (can be changed any time) ----------

    @Transactional
    public void decideCustomer(Integer userId, boolean approve) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
        user.setApprovalStatus(approve ? "APPROVED" : "REJECTED");
        userRepository.save(user);
    }

    @Transactional
    public void decideStaff(Integer userId, boolean approve, String position) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
        Staff staff = staffRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Staff record not found."));

        if (approve) {
            String finalPosition = (position == null || position.isBlank()) ? staff.getRole() : position;
            if (!STAFF_POSITIONS.contains(finalPosition)) {
                throw new IllegalArgumentException("Invalid position.");
            }
            staff.setRole(finalPosition);
            user.setRole("Manager".equals(finalPosition) ? "MANAGER" : "EMPLOYEE");
            user.setApprovalStatus("APPROVED");
            staffRepository.save(staff);
        } else {
            user.setApprovalStatus("REJECTED");
        }
        userRepository.save(user);
    }
}