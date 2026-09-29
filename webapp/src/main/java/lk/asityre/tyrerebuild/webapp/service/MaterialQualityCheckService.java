package lk.asityre.tyrerebuild.webapp.service;

import lk.asityre.tyrerebuild.webapp.model.MaterialQualityCheck;
import lk.asityre.tyrerebuild.webapp.repository.MaterialQualityCheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MaterialQualityCheckService {

    @Autowired
    private MaterialQualityCheckRepository mqcRepository;

    public List<MaterialQualityCheck> getAllChecks() {
        return mqcRepository.findAll();
    }

    public MaterialQualityCheck getCheckById(Integer id) {
        return mqcRepository.findById(id).orElse(null);
    }
    
    public List<MaterialQualityCheck> getChecksByPurchaseId(Integer purchaseId) {
        return mqcRepository.findByPurchaseId(purchaseId);
    }

    public MaterialQualityCheck saveCheck(MaterialQualityCheck check) {
        if (check.getCheckDate() == null) {
            check.setCheckDate(LocalDate.now());
        }
        if (check.getResult() == null) {
            check.setResult("Pending");
        }
        return mqcRepository.save(check);
    }

    public void deleteCheck(Integer id) {
        mqcRepository.deleteById(id);
    }

    public MaterialQualityCheck updateCheck(Integer id, MaterialQualityCheck updatedDetails) {
        MaterialQualityCheck existingCheck = mqcRepository.findById(id).orElse(null);
        if (existingCheck != null) {
            existingCheck.setQualityGrade(updatedDetails.getQualityGrade());
            existingCheck.setResult(updatedDetails.getResult());
            existingCheck.setRemarks(updatedDetails.getRemarks());
            existingCheck.setCheckedBy(updatedDetails.getCheckedBy());
            return mqcRepository.save(existingCheck);
        }
        return null;
    }
}