package lk.asityre.tyrerebuild.webapp.service;

import lk.asityre.tyrerebuild.webapp.model.Tyre;
import lk.asityre.tyrerebuild.webapp.repository.TyreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProductionService {

    private final TyreRepository tyreRepository;

    public ProductionService(TyreRepository tyreRepository) {
        this.tyreRepository = tyreRepository;
    }


    public List<Tyre> getAllTyres() {
        return tyreRepository.findAllByOrderByTyreIdDesc();
    }


    public Tyre getTyre(Integer id) {
        return tyreRepository.findById(id).orElse(null);
    }


    public void updateStage(Integer tyreId, String stage) {

        Tyre tyre = getTyre(tyreId);

        if (tyre == null) {
            return;
        }

        Tyre.RebuildStage rebuildStage =
                Tyre.RebuildStage.valueOf(stage);

        tyre.setRebuildStage(rebuildStage);

        if (rebuildStage == Tyre.RebuildStage.COMPLETED) {
            tyre.setCompletedDate(LocalDate.now());
        }

        tyreRepository.save(tyre);
    }


    public void saveTyre(Tyre tyre) {

        if (tyre.getReceivedDate() == null) {
            tyre.setReceivedDate(LocalDate.now());
        }

        if (tyre.getRebuildStage() == null) {
            tyre.setRebuildStage(Tyre.RebuildStage.RECEIVED);
        }

        tyreRepository.save(tyre);
    }
}