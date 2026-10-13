package lk.asityre.tyrerebuild.webapp.service;

import lk.asityre.tyrerebuild.webapp.repository.StaffRepository;
import org.springframework.stereotype.Service;

@Service
public class StaffService {

    private final StaffRepository staffRepository;

    public StaffService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

}