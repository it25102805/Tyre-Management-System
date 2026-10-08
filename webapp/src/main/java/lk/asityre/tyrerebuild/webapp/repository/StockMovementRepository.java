package lk.asityre.tyrerebuild.webapp.repository;

import lk.asityre.tyrerebuild.webapp.model.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovement, Integer> {

    List<StockMovement> findAllByOrderByMovementIdDesc();

    @Query("SELECT COALESCE(SUM(m.quantity), 0) " +
            "FROM StockMovement m " +
            "WHERE m.materialTypeId = :materialTypeId " +
            "AND m.movementType = :movementType")
    int sumQuantity(@Param("materialTypeId") Integer materialTypeId,
                    @Param("movementType") String movementType);
}



