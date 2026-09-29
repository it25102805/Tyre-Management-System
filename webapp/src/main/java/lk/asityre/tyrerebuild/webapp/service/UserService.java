package lk.asityre.tyrerebuild.webapp.service;

import lk.asityre.tyrerebuild.webapp.model.User;
import lk.asityre.tyrerebuild.webapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

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