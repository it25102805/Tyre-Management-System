package lk.asityre.tyrerebuild.webapp.controller;

import lk.asityre.tyrerebuild.webapp.model.MaterialPurchase;
import lk.asityre.tyrerebuild.webapp.service.MaterialPurchaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.List;

@RestController
@RequestMapping("/api/material-purchases")
public class MaterialPurchaseController {

    @Autowired
    private MaterialPurchaseService materialPurchaseService;

    @GetMapping
    public List<MaterialPurchase> getAll(@RequestParam(required = false) String status) {
        return materialPurchaseService.getPurchases(status);
    }

    @GetMapping("/{id}")
    public MaterialPurchase getById(@PathVariable Integer id) {
        return materialPurchaseService.getMaterialPurchaseById(id);
    }

    @PostMapping
    public MaterialPurchase create(
            @RequestBody MaterialPurchase materialPurchase, HttpSession session) {

        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            throw new IllegalArgumentException("User is not logged in.");
        }

        return materialPurchaseService.saveMaterialPurchase(
                materialPurchase,
                userId
        );
    }

    @PutMapping("/{id}/status")
    public MaterialPurchase updateStatus(@PathVariable Integer id, @RequestParam String status) {
        return materialPurchaseService.updatePurchaseStatus(id, status);
    }

    @PutMapping("/{id}")
    public MaterialPurchase update(@PathVariable Integer id, @RequestBody MaterialPurchase purchaseDetails) {
        return materialPurchaseService.updatePurchase(id, purchaseDetails);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}