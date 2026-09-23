package lk.asityre.tyrerebuild.webapp.controller;

import lk.asityre.tyrerebuild.webapp.model.StockMovement;
import lk.asityre.tyrerebuild.webapp.service.StockMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock-movement")
public class StockMovementController {
    @Autowired
    private StockMovementService stockMovementService;

    @GetMapping
    public List<StockMovement> getAll() {
        return stockMovementService.getAllStockMovement();
    }

    @GetMapping("/{id}")
    public StockMovement getById(@PathVariable Integer id) {
        return stockMovementService.getStockMovementById(id);
    }

    @PostMapping
    public StockMovement create(@RequestBody StockMovement stockMovement) {
        return stockMovementService.saveStockMovement(stockMovement);
    }

    @PutMapping("/{id}")
    public StockMovement update(@PathVariable Integer id, @RequestBody StockMovement movement) {
        return stockMovementService.updateMovement(id, movement);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        stockMovementService.deleteStockMovement(id);
    }
}