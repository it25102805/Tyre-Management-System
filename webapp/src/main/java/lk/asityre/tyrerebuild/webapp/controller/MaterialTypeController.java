package lk.asityre.tyrerebuild.webapp.controller;

import lk.asityre.tyrerebuild.webapp.model.MaterialType;
import lk.asityre.tyrerebuild.webapp.service.MaterialTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/material-types")
public class MaterialTypeController {

    @Autowired
    private MaterialTypeService materialTypeService;

    @GetMapping
    public List<MaterialType> getAll() {
        return materialTypeService.getAllMaterialTypes();
    }

    @GetMapping("/{id}")
    public MaterialType getById(@PathVariable Integer id) {
        return materialTypeService.getMaterialTypeById(id);
    }

    @PostMapping
    public MaterialType create(@RequestBody MaterialType materialType) {
        return materialTypeService.saveMaterialType(materialType);
    }

    @PutMapping("/{id}")
    public MaterialType update(@PathVariable Integer id, @RequestBody MaterialType materialType) {
        return materialTypeService.updateMaterialType(id, materialType);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        materialTypeService.deleteMaterialType(id);
    }
}