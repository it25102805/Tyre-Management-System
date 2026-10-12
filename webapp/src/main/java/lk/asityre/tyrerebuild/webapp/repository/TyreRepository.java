package lk.asityre.tyrerebuild.webapp.repository;

import lk.asityre.tyrerebuild.webapp.model.Tyre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TyreRepository extends JpaRepository<Tyre, Integer> {

    List<Tyre> findAllByOrderByTyreIdDesc();

    List<Tyre> findByRebuildStage(Tyre.RebuildStage rebuildStage);

    List<Tyre> findByOrderId(Integer orderId);
}