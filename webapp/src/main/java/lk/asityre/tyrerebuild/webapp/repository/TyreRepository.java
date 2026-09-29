package lk.asityre.tyrerebuild.webapp.repository;

import lk.asityre.tyrerebuild.webapp.model.Tyre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TyreRepository extends JpaRepository<Tyre, Integer> {

    List<Tyre> findAllByOrderByTyreIdDesc();

    List<Tyre> findByRebuildStage(Tyre.RebuildStage rebuildStage);

    List<Tyre> findByOrderId(Integer orderId);
}