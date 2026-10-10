package lk.asityre.tyrerebuild.webapp.repository;

import lk.asityre.tyrerebuild.webapp.model.MaterialQualityCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MaterialQualityCheckRepository extends JpaRepository<MaterialQualityCheck, Integer> {
    List<MaterialQualityCheck> findAllByOrderByMaterialCheckIdDesc();

    long countByPurchaseId(Integer purchaseId);
}
