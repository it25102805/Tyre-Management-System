package lk.asityre.tyrerebuild.webapp.controller;

import lk.asityre.tyrerebuild.webapp.model.MaterialType;
import lk.asityre.tyrerebuild.webapp.service.MaterialPurchaseService;
import lk.asityre.tyrerebuild.webapp.service.MaterialTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MaterialTypeController {

    @Autowired
    private MaterialTypeService materialTypeService;

    @Autowired
    private MaterialPurchaseService materialPurchaseService;

    @GetMapping("/sourcing/material-types")
    public String viewMaterialTypes(Model model) {
        List<MaterialType> materialTypes = materialTypeService.getAllMaterialTypes();
        model.addAttribute("materialTypes", materialTypes);


        List<Integer> usedMaterialTypeIds = new ArrayList<>();
        for (MaterialType type : materialTypes) {
            Integer materialTypeId = type.getMaterialTypeId();
            if (materialPurchaseService.isMaterialTypeUsed(materialTypeId)) {
                usedMaterialTypeIds.add(materialTypeId);
            }
        }

        model.addAttribute("usedMaterialTypeIds", usedMaterialTypeIds);
        model.addAttribute("newMaterialType", new MaterialType());
        return "material-types";
    }

    @PostMapping("/sourcing/material-types")
    public String saveMaterialType(@ModelAttribute("newMaterialType") MaterialType materialType) {
        Integer materialTypeId = materialType.getMaterialTypeId();
        if (materialTypeId == null) {
            materialTypeService.saveMaterialType(materialType);
        } else {
            materialTypeService.updateMaterialType(materialTypeId, materialType);
        }
        return "redirect:/sourcing/material-types";
    }

    @PostMapping("/sourcing/material-types/delete/{id}")
    public String deleteMaterialType(@PathVariable Integer id) {
        boolean isUsed = materialPurchaseService.isMaterialTypeUsed(id);
        if (!isUsed) {
            materialTypeService.deleteMaterialType(id);
        }
        return "redirect:/sourcing/material-types";
    }
}