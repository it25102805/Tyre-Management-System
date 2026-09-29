package lk.asityre.tyrerebuild.webapp.repository;

import lk.asityre.tyrerebuild.webapp.model.ProductionQualityCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductionQualityCheckRepository
        extends JpaRepository<ProductionQualityCheck, Integer> {

    List<ProductionQualityCheck> findByTyreId(Integer tyreId);
}