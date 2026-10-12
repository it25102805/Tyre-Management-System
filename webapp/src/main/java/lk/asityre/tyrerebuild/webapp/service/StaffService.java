package lk.asityre.tyrerebuild.webapp.service;

import lk.asityre.tyrerebuild.webapp.model.Staff;
import lk.asityre.tyrerebuild.webapp.repository.StaffRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffService {

    private final StaffRepository staffRepository;

    public StaffService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    public Optional<Staff> getStaffByUserId(Integer userId) {
        return staffRepository.findByUserId(userId);
    }

    public Optional<Staff> getStaffById(Integer staffId) {
        return staffRepository.findById(staffId);
    }
}