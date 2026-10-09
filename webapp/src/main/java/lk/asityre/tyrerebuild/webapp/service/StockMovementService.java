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

    // Usable stock = received - sent to quality + returned - issued
    public int getUsableStock(Integer typeId) {
        return movementRepository.sumQuantity(typeId, "RECEIVED_FROM_PURCHASE")
                - movementRepository.sumQuantity(typeId, "SENT_TO_QUALITY")
                + movementRepository.sumQuantity(typeId, "RETURNED_FROM_QUALITY")
                - movementRepository.sumQuantity(typeId, "ISSUED_TO_PRODUCTION");
    }

    // Items currently at quality check
    public int getAtQuality(Integer typeId) {
        return movementRepository.sumQuantity(typeId, "SENT_TO_QUALITY")
                - movementRepository.sumQuantity(typeId, "RETURNED_FROM_QUALITY");
    }

    @Transactional
    public StockMovement record(StockMovement m, Integer userId) {

        if (m.getMaterialTypeId() == null) {
            throw new IllegalArgumentException("Select a material type.");
        }

        if (m.getQuantity() == null || m.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }

        String type = m.getMovementType();

        if ("SENT_TO_QUALITY".equals(type)
                || "ISSUED_TO_PRODUCTION".equals(type)) {

            if (m.getQuantity() > getUsableStock(m.getMaterialTypeId())) {
                throw new IllegalArgumentException("Not enough usable stock.");
            }

        } else if ("RETURNED_FROM_QUALITY".equals(type)) {

            if (m.getQuantity() > getAtQuality(m.getMaterialTypeId())) {
                throw new IllegalArgumentException(
                        "Quantity is more than what is at quality check.");
            }

        } else {
            throw new IllegalArgumentException("Invalid movement type.");
        }

        Staff staff = staffRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "The logged-in user is not registered as a staff member."));

        m.setHandledBy(staff.getStaffId());
        m.setMovementDate(LocalDate.now());

        return movementRepository.save(m);
    }
}