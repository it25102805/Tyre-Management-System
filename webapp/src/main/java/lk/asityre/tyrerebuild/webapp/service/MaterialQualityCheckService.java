package lk.asityre.tyrerebuild.webapp.service;

import lk.asityre.tyrerebuild.webapp.model.*;
import lk.asityre.tyrerebuild.webapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class MaterialQualityCheckService {

    @Autowired private MaterialQualityCheckRepository checkRepository;
    @Autowired private MaterialPurchaseRepository purchaseRepository;
    @Autowired private StockMovementRepository movementRepository;
    @Autowired private StaffRepository staffRepository;

    public List<MaterialQualityCheck> getAll() {
        return checkRepository.findAllByOrderByMaterialCheckIdDesc();
    }

    public List<MaterialPurchase> getAwaitingCheck() {
        return purchaseRepository.findByStatusOrderByPurchaseIdDesc("Pending");
    }

    @Transactional
    public void recordCheck(Integer purchaseId, String grade, String result,
                            String remarks, Integer userId) {

        if (!"Passed".equals(result) && !"Failed".equals(result)) {
            throw new IllegalArgumentException("Result must be Passed or Failed.");
        }

        MaterialPurchase p = purchaseRepository.findById(purchaseId).orElse(null);

        if (p == null) {
            throw new IllegalArgumentException("Purchase not found.");
        }

        if (!"Pending".equals(p.getStatus())) {
            throw new IllegalArgumentException("Only arrived (Pending) purchases can be checked.");
        }

        Staff staff = staffRepository.findByUserId(userId).orElse(null);

        if (staff == null) {
            throw new IllegalArgumentException("The logged in user is not registered as a staff member.");
        }

        MaterialQualityCheck c = new MaterialQualityCheck();
        c.setPurchaseId(purchaseId);
        c.setCheckedBy(staff.getStaffId());
        c.setCheckDate(LocalDate.now());
        c.setQualityGrade(grade);
        c.setResult(result);
        c.setRemarks(remarks);
        checkRepository.save(c);

        if ("Passed".equals(result)) {
            p.setStatus("Received");
            purchaseRepository.save(p);

            StockMovement m = new StockMovement();
            m.setMaterialTypeId(p.getMaterialTypeId());
            m.setOrderId(p.getOrderId());
            m.setMovementType("RECEIVED_FROM_PURCHASE");
            m.setQuantity(p.getQuantity());
            m.setMovementDate(LocalDate.now());
            m.setHandledBy(staff.getStaffId());
            movementRepository.save(m);
        }
        // Failed: purchase stays Pending, can be checked again
    }
}