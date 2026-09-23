package lk.asityre.tyrerebuild.webapp.controller;

import lk.asityre.tyrerebuild.webapp.model.MaterialPurchase;
import lk.asityre.tyrerebuild.webapp.service.MaterialPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/material-purchases")
public class MaterialPurchaseController {

    @Autowired
    private MaterialPurchaseService materialPurchaseService;

    @GetMapping
    public List<MaterialPurchase> getAll() {
        return materialPurchaseService.getAllMaterialPurchase();
    }

    @GetMapping("/{id}")
    public MaterialPurchase getById(@PathVariable Integer id) {
        return materialPurchaseService.getMaterialPurchaseById(id);
    }

    @PostMapping
    public MaterialPurchase create(@RequestBody MaterialPurchase materialPurchase) {
        return materialPurchaseService.saveMaterialPurchase(materialPurchase);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        materialPurchaseService.deleteMaterialPurchase(id);
    }

    @PutMapping("/{id}/status")
    public MaterialPurchase updateStatus(@PathVariable Integer id, @RequestParam String status) {
        return materialPurchaseService.updatePurchaseStatus(id, status);
    }

    @PutMapping("/{id}")
    public MaterialPurchase update(@PathVariable Integer id, @RequestBody MaterialPurchase purchaseDetails) {
        return materialPurchaseService.updatePurchase(id, purchaseDetails);
    }
}