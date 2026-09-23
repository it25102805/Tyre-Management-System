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

@Controller
public class MaterialTypeViewController {

    @Autowired
    private MaterialTypeService materialTypeService;

    @GetMapping("/material-types")
    public String viewMaterialTypes(Model model) {

        model.addAttribute("materialTypes",
                materialTypeService.getAllMaterialTypes());

        model.addAttribute("newMaterialType",
                new MaterialType());

        return "material-types";
    }

    @PostMapping("/material-types")
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

        return "redirect:/material-types";
    }

    @PostMapping("/material-types/delete/{id}")
    public String deleteMaterialType(@PathVariable Integer id) {

        materialTypeService.deleteMaterialType(id);

        return "redirect:/material-types";
    }
}