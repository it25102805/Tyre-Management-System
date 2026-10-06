package lk.asityre.tyrerebuild.webapp.repository;

import lk.asityre.tyrerebuild.webapp.model.ProductionQualityCheck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductionQualityCheckRepository
        extends JpaRepository<ProductionQualityCheck, Integer> {

    List<ProductionQualityCheck> findByTyreId(Integer tyreId);
}