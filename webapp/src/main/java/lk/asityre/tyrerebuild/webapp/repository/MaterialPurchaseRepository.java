package lk.asityre.tyrerebuild.webapp.repository;

import lk.asityre.tyrerebuild.webapp.model.MaterialPurchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterialPurchaseRepository extends JpaRepository<MaterialPurchase, Integer> {
    List<MaterialPurchase> findAllByOrderByPurchaseIdDesc();
    List<MaterialPurchase> findByStatusOrderByPurchaseIdDesc(String status);
}
