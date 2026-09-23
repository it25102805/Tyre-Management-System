package lk.asityre.tyrerebuild.webapp.controller;

import lk.asityre.tyrerebuild.webapp.model.Staff;
import lk.asityre.tyrerebuild.webapp.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
public class StaffController {
    @Autowired
    private StaffService staffService;

    @GetMapping
    public List<Staff> getAll() {
        return staffService.getAllStaff();
    }

    @GetMapping("/{id}")
    public Staff getById(@PathVariable Integer id) {
        return staffService.getStaffById(id);
    }

    @PostMapping
    public Staff create(@RequestBody Staff staff) {
        return staffService.saveStaff(staff);
    }

    @PutMapping("/{id}")
    public Staff update(@PathVariable Integer id, @RequestBody Staff staff) {
        return staffService.updateStaff(id, staff);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        staffService.deleteStaff(id);
    }
}