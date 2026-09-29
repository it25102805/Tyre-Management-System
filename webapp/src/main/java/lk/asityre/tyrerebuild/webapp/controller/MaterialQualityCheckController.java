package lk.asityre.tyrerebuild.webapp.controller;

import lk.asityre.tyrerebuild.webapp.model.MaterialQualityCheck;
import lk.asityre.tyrerebuild.webapp.service.MaterialQualityCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/material-quality-checks")
public class MaterialQualityCheckController {

    @Autowired
    private MaterialQualityCheckService mqcService;

    @GetMapping
    public List<MaterialQualityCheck> getAll() {
        return mqcService.getAllChecks();
    }

    @GetMapping("/{id}")
    public MaterialQualityCheck getById(@PathVariable Integer id) {
        return mqcService.getCheckById(id);
    }

    @GetMapping("/purchase/{purchaseId}")
    public List<MaterialQualityCheck> getByPurchaseId(@PathVariable Integer purchaseId) {
        return mqcService.getChecksByPurchaseId(purchaseId);
    }

    @PostMapping
    public MaterialQualityCheck create(@RequestBody MaterialQualityCheck check) {
        return mqcService.saveCheck(check);
    }

    @PutMapping("/{id}")
    public MaterialQualityCheck update(@PathVariable Integer id, @RequestBody MaterialQualityCheck check) {
        return mqcService.updateCheck(id, check);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        mqcService.deleteCheck(id);
    }
}