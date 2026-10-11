package lk.asityre.tyrerebuild.webapp.repository;

import lk.asityre.tyrerebuild.webapp.model.MaterialPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD
@Repository
public interface MaterialPurchaseRepository extends JpaRepository<MaterialPurchase, Integer> {
=======
import java.util.List;

public interface MaterialPurchaseRepository
        extends JpaRepository<MaterialPurchase, Integer> {

    List<MaterialPurchase> findAllByOrderByPurchaseIdDesc();

    List<MaterialPurchase> findByStatusOrderByPurchaseIdDesc(String status);

    boolean existsByMaterialTypeId(Integer materialTypeId);
>>>>>>> origin/main
}