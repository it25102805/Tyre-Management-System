package lk.asityre.tyrerebuild.webapp.service;

import lk.asityre.tyrerebuild.webapp.model.StockMovement;
import lk.asityre.tyrerebuild.webapp.model.Staff;
import lk.asityre.tyrerebuild.webapp.repository.StaffRepository;
import lk.asityre.tyrerebuild.webapp.repository.StockMovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class StockMovementService {

    @Autowired
    private StockMovementRepository movementRepository;

    @Autowired
    private StaffRepository staffRepository;

    public List<StockMovement> getAll() {
        return movementRepository.findAllByOrderByMovementIdDesc();
    }

    // Usable stock = received - issued
    public int getUsableStock(Integer typeId) {
        return movementRepository.sumQuantity(typeId, "RECEIVED_FROM_PURCHASE")
                - movementRepository.sumQuantity(typeId, "ISSUED_TO_PRODUCTION");
    }

    @Transactional
    public StockMovement record(StockMovement m, Integer userId) {

        if (m.getMaterialTypeId() == null) {
            throw new IllegalArgumentException("Select a material type.");
        }

        if (m.getQuantity() == null || m.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }

        m.setMovementType("ISSUED_TO_PRODUCTION");

        if (m.getQuantity() > getUsableStock(m.getMaterialTypeId())) {
            throw new IllegalArgumentException("Not enough usable stock.");
        }

        Staff staff = staffRepository.findByUserId(userId).orElse(null);

        if (staff == null) {
            throw new IllegalArgumentException("The logged-in user is not registered as a staff member.");
        }

        m.setHandledBy(staff.getStaffId());
        m.setMovementDate(LocalDate.now());

        return movementRepository.save(m);
    }
}