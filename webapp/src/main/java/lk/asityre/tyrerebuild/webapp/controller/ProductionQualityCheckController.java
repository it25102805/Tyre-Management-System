package lk.asityre.tyrerebuild.webapp.controller;

import lk.asityre.tyrerebuild.webapp.model.ProductionQualityCheck;
import lk.asityre.tyrerebuild.webapp.service.ProductionQualityCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/production-quality-checks")
public class ProductionQualityCheckController {

    @Autowired
    private ProductionQualityCheckService qualityCheckService;

    @GetMapping
    public List<ProductionQualityCheck> getAll() {
        return qualityCheckService.getAllChecks();
    }

    @GetMapping("/{id}")
    public ProductionQualityCheck getById(@PathVariable Integer id) {
        return qualityCheckService.getCheckById(id);
    }

    @GetMapping("/tyre/{tyreId}")
    public List<ProductionQualityCheck> getByTyreId(@PathVariable Integer tyreId) {
        return qualityCheckService.getChecksByTyreId(tyreId);
    }

    @PostMapping
    public ProductionQualityCheck create(@RequestBody ProductionQualityCheck check) {
        return qualityCheckService.saveCheck(check);
    }

    @PutMapping("/{id}")
    public ProductionQualityCheck update(@PathVariable Integer id, @RequestBody ProductionQualityCheck check) {
        return qualityCheckService.updateCheck(id, check);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        qualityCheckService.deleteCheck(id);
    }
}