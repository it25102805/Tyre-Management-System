package lk.asityre.tyrerebuild.webapp.service;

import lk.asityre.tyrerebuild.webapp.model.MaterialPurchase;
import lk.asityre.tyrerebuild.webapp.model.Staff;
import lk.asityre.tyrerebuild.webapp.repository.MaterialPurchaseRepository;
import lk.asityre.tyrerebuild.webapp.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class MaterialPurchaseService {

    @Autowired
    private MaterialPurchaseRepository materialPurchaseRepository;

    @Autowired
    private StaffRepository staffRepository;

    public List<MaterialPurchase> getPurchases(String status) {
        if (status == null || status.isEmpty()) {
            return materialPurchaseRepository.findAllByOrderByPurchaseIdDesc();
        }
        return materialPurchaseRepository.findByStatusOrderByPurchaseIdDesc(status);
    }

    public MaterialPurchase getMaterialPurchaseById(Integer id) {
        Optional<MaterialPurchase> result = materialPurchaseRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        return null;
    }


    public MaterialPurchase updatePurchaseStatus(Integer id, String newStatus) {
        Optional<MaterialPurchase> result = materialPurchaseRepository.findById(id);
        if (result.isPresent()) {
            MaterialPurchase existingPurchase = result.get();
            String currentStatus = existingPurchase.getStatus();

            boolean validStep =
                    ("Pending".equals(currentStatus) && "Ordered".equals(newStatus)) || ("Ordered".equals(currentStatus) && "Received".equals(newStatus));

            if (!validStep) {
                throw new IllegalArgumentException(
                        "Cannot change status from " + currentStatus + " to " + newStatus);
            }

            existingPurchase.setStatus(newStatus);
            return materialPurchaseRepository.save(existingPurchase);
        } else {
            return null;
        }
    }

    public MaterialPurchase updatePurchase(Integer id, MaterialPurchase updatedDetails) {
        Optional<MaterialPurchase> result = materialPurchaseRepository.findById(id);
        if (result.isPresent()) {
            MaterialPurchase existingPurchase = result.get();

            if ("Received".equals(existingPurchase.getStatus())) {
                throw new IllegalArgumentException("A Received purchase cannot be edited");
            }
            if (updatedDetails.getQuantity() == null || updatedDetails.getQuantity() <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than zero");
            }

            existingPurchase.setQuantity(updatedDetails.getQuantity());
            existingPurchase.setHandledBy(updatedDetails.getHandledBy());
            return materialPurchaseRepository.save(existingPurchase);
        } else {
            return null;
        }
    }

    /*return = “I’m finished, here’s my result.”
      throw = “I can’t continue, something went wrong.”
    */

    public MaterialPurchase saveMaterialPurchase(MaterialPurchase materialPurchase,Integer userId) {
        if (materialPurchase.getQuantity() == null || materialPurchase.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        if (materialPurchase.getStatus() == null || materialPurchase.getStatus().isEmpty()) {
            materialPurchase.setStatus("Pending");
        }
        if (materialPurchase.getPurchaseDate() == null) {
            materialPurchase.setPurchaseDate(LocalDate.now());
        }

        // Find the staff member belonging to the logged-in user
        Optional<Staff> staffResult = staffRepository.findByUserId(userId);

        if (staffResult.isEmpty()) {
            throw new IllegalArgumentException(
                    "The logged-in user is not registered as a staff member."
            );
        }

        // Save staff_id into handled_by
        Staff staff = staffResult.get();
        materialPurchase.setHandledBy(staff.getStaffId());
        return materialPurchaseRepository.save(materialPurchase);
    }

}