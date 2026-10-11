package lk.asityre.tyrerebuild.webapp.controller;

import lk.asityre.tyrerebuild.webapp.model.MaterialType;
import lk.asityre.tyrerebuild.webapp.service.MaterialTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import lk.asityre.tyrerebuild.webapp.service.MaterialPurchaseService;
import java.util.List;
import java.util.*;

@Controller
public class MaterialTypeViewController {

    @Autowired
    private MaterialTypeService materialTypeService;
    @Autowired
    private MaterialPurchaseService materialPurchaseService;

    @GetMapping("/sourcing/material-types")
    public String viewMaterialTypes(Model model) {

        List<MaterialType> materialTypes =
                materialTypeService.getAllMaterialTypes();

        model.addAttribute(
                "materialTypes",
                materialTypes);

        List<Integer> usedMaterialTypeIds =
                new ArrayList<>();

        for (MaterialType type : materialTypes) {

            if (materialPurchaseService.isMaterialTypeUsed(
                    type.getMaterialTypeId())) {

                usedMaterialTypeIds.add(
                        type.getMaterialTypeId());
            }
        }

        model.addAttribute(
                "usedMaterialTypeIds",
                usedMaterialTypeIds);

        model.addAttribute(
                "newMaterialType",
                new MaterialType());

        return "material-types";
    }

    @PostMapping("/sourcing/material-types")
    public String saveMaterialType(
            @ModelAttribute("newMaterialType") MaterialType materialType) {

        if (materialType.getMaterialTypeId() == null) {

            // ADD NEW MATERIAL TYPE
            materialTypeService.saveMaterialType(materialType);

        } else {

            // UPDATE EXISTING MATERIAL TYPE
            materialTypeService.updateMaterialType(
                    materialType.getMaterialTypeId(),
                    materialType
            );
        }

        return "redirect:/sourcing/material-types";
    }

    @PostMapping("/sourcing/material-types/delete/{id}")
    public String deleteMaterialType(@PathVariable Integer id) {

        if (materialPurchaseService.isMaterialTypeUsed(id)) {
            return "redirect:/sourcing/material-types";
        }

        materialTypeService.deleteMaterialType(id);

        return "redirect:/sourcing/material-types";
    }
}