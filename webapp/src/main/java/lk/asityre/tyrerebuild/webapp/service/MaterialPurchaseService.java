package lk.asityre.tyrerebuild.webapp.service;

import lk.asityre.tyrerebuild.webapp.model.MaterialPurchase;
import lk.asityre.tyrerebuild.webapp.repository.MaterialPurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialPurchaseService {

    @Autowired
    private MaterialPurchaseRepository materialPurchaseRepository;

    public List<MaterialPurchase> getAllMaterialPurchase() {
        return materialPurchaseRepository.findAll();
    }

    public MaterialPurchase getMaterialPurchaseById(Integer id) {
        return materialPurchaseRepository.findById(id).orElse(null);
    }

    public void deleteMaterialPurchase(Integer id) {
        materialPurchaseRepository.deleteById(id);
    }

    public MaterialPurchase updatePurchaseStatus(Integer id, String newStatus) {
        MaterialPurchase existingPurchase = materialPurchaseRepository.findById(id).orElse(null);
        if (existingPurchase != null) {
            existingPurchase.setStatus(newStatus);
            return materialPurchaseRepository.save(existingPurchase);
        }
        return null;
    }

    public MaterialPurchase updatePurchase(Integer id, MaterialPurchase updatedDetails) {
        MaterialPurchase existingPurchase = materialPurchaseRepository.findById(id).orElse(null);
        if (existingPurchase != null) {
            existingPurchase.setQuantity(updatedDetails.getQuantity());
            existingPurchase.setStatus(updatedDetails.getStatus());
            existingPurchase.setHandledBy(updatedDetails.getHandledBy());
            return materialPurchaseRepository.save(existingPurchase);
        }
        return null;
    }

    public MaterialPurchase saveMaterialPurchase(MaterialPurchase materialPurchase) {
        if (materialPurchase.getQuantity() == null || materialPurchase.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        return materialPurchaseRepository.save(materialPurchase);
    }


}