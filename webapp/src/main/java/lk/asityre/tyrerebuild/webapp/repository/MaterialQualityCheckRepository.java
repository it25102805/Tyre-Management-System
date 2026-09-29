package lk.asityre.tyrerebuild.webapp.repository;

import lk.asityre.tyrerebuild.webapp.model.MaterialQualityCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialQualityCheckRepository extends JpaRepository<MaterialQualityCheck, Integer> {
    List<MaterialQualityCheck> findByPurchaseId(Integer purchaseId);
}