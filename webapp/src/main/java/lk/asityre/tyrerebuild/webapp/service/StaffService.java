package lk.asityre.tyrerebuild.webapp.service;

import lk.asityre.tyrerebuild.webapp.model.Staff;
import lk.asityre.tyrerebuild.webapp.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    public Staff getStaffById(Integer id) {
        return staffRepository.findById(id).orElse(null);
    }

    public Staff saveStaff(Staff staff) {
        return staffRepository.save(staff);
    }

    public void deleteStaff(Integer id) {
        staffRepository.deleteById(id);
    }

    public Staff updateStaff(Integer id, Staff updatedStaff) {
        Staff existingStaff = staffRepository.findById(id).orElse(null);
        if (existingStaff != null) {
            existingStaff.setFirstName(updatedStaff.getFirstName());
            existingStaff.setLastName(updatedStaff.getLastName());
            existingStaff.setRole(updatedStaff.getRole());
            existingStaff.setContactNumber(updatedStaff.getContactNumber());
            return staffRepository.save(existingStaff);
        }
        return null;
    }
}
