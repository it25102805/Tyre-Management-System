package lk.asityre.tyrerebuild.webapp.service;

import lk.asityre.tyrerebuild.webapp.model.ProductionQualityCheck;
import lk.asityre.tyrerebuild.webapp.repository.ProductionQualityCheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductionQualityCheckService {

    @Autowired
    private ProductionQualityCheckRepository qualityCheckRepository;

    public List<ProductionQualityCheck> getAllChecks() {
        return qualityCheckRepository.findAll();
    }

    public ProductionQualityCheck getCheckById(Integer id) {
        return qualityCheckRepository.findById(id).orElse(null);
    }

    public List<ProductionQualityCheck> getChecksByTyreId(Integer tyreId) {
        return qualityCheckRepository.findByTyreId(tyreId);
    }

    public ProductionQualityCheck saveCheck(ProductionQualityCheck check) {
        return qualityCheckRepository.save(check);
    }

    public void deleteCheck(Integer id) {
        qualityCheckRepository.deleteById(id);
    }

    public ProductionQualityCheck updateCheck(Integer id, ProductionQualityCheck updatedCheck) {
        ProductionQualityCheck existingCheck = qualityCheckRepository.findById(id).orElse(null);
        if (existingCheck != null) {
            existingCheck.setQualityGrade(updatedCheck.getQualityGrade());
            existingCheck.setResult(updatedCheck.getResult());
            existingCheck.setRemarks(updatedCheck.getRemarks());
            existingCheck.setCheckedBy(updatedCheck.getCheckedBy());
            return qualityCheckRepository.save(existingCheck);
        }
        return null;
    }
}