package lk.asityre.tyrerebuild.webapp.service;

import lk.asityre.tyrerebuild.webapp.model.StockMovement;
import lk.asityre.tyrerebuild.webapp.repository.StockMovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockMovementService {

    @Autowired
    private StockMovementRepository stockMovementRepository;

    public List<StockMovement> getAllStockMovement() {
        return stockMovementRepository.findAll();
    }

    public StockMovement getStockMovementById(Integer id) {
        return stockMovementRepository.findById(id).orElse(null);
    }

    public StockMovement saveStockMovement(StockMovement stockMovement) {
        return stockMovementRepository.save(stockMovement);
    }

    public void deleteStockMovement(Integer id) {
        stockMovementRepository.deleteById(id);
    }

    public StockMovement updateMovement(Integer id, StockMovement updatedMovement) {
        StockMovement existingMovement = stockMovementRepository.findById(id).orElse(null);
        if (existingMovement != null) {
            existingMovement.setMovementType(updatedMovement.getMovementType());
            existingMovement.setQuantity(updatedMovement.getQuantity());
            existingMovement.setHandledBy(updatedMovement.getHandledBy());
            return stockMovementRepository.save(existingMovement);
        }
        return null;
    }
}
