package lk.asityre.tyrerebuild.webapp.service;

import lk.asityre.tyrerebuild.webapp.model.MaterialPurchase;
import lk.asityre.tyrerebuild.webapp.model.MaterialQualityCheck;
import lk.asityre.tyrerebuild.webapp.model.Staff;
import lk.asityre.tyrerebuild.webapp.model.StockMovement;
import lk.asityre.tyrerebuild.webapp.repository.MaterialPurchaseRepository;
import lk.asityre.tyrerebuild.webapp.repository.MaterialQualityCheckRepository;
import lk.asityre.tyrerebuild.webapp.repository.StaffRepository;
import lk.asityre.tyrerebuild.webapp.repository.StockMovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MaterialQualityCheckService {

    @Autowired
    private MaterialQualityCheckRepository checkRepository;
    @Autowired
    private MaterialPurchaseRepository purchaseRepository;
    @Autowired
    private StockMovementRepository movementRepository;
    @Autowired
    private StaffRepository staffRepository;

    public List<MaterialQualityCheck> getAll() {
        return checkRepository.findAllByOrderByMaterialCheckIdDesc();
    }

    public List<MaterialPurchase> getAwaitingCheck() {
        return purchaseRepository.findByStatusOrderByPurchaseIdDesc("Pending");
    }

    @Transactional
    public void recordCheck(Integer purchaseId, String grade, String result, String remarks, Integer userId) {

        if (!"Passed".equals(result) && !"Failed".equals(result)) {
            throw new IllegalArgumentException("Result must be Passed or Failed.");
        }

        Optional<MaterialPurchase> purchaseResult = purchaseRepository.findById(purchaseId);
        if (purchaseResult.isEmpty()) {
            throw new IllegalArgumentException("Purchase not found.");
        }
        MaterialPurchase p = purchaseResult.get();

        if (!"Pending".equals(p.getStatus())) {
            throw new IllegalArgumentException("Only arrived (Pending) purchases can be checked.");
        }

        Optional<Staff> staffResult = staffRepository.findByUserId(userId);
        if (staffResult.isEmpty()) {
            throw new IllegalArgumentException("The logged in user is not registered as a staff member.");
        }
        Staff staff = staffResult.get();

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
    }
}