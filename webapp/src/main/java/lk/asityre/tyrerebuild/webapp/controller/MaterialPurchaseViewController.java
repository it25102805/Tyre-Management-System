package lk.asityre.tyrerebuild.webapp.controller;

import lk.asityre.tyrerebuild.webapp.model.MaterialPurchase;
import lk.asityre.tyrerebuild.webapp.model.MaterialType;
import lk.asityre.tyrerebuild.webapp.service.MaterialPurchaseService;
import lk.asityre.tyrerebuild.webapp.service.MaterialTypeService;
import lk.asityre.tyrerebuild.webapp.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MaterialPurchaseViewController {

    @Autowired
    private MaterialPurchaseService materialPurchaseService;

    @Autowired
    private MaterialTypeService materialTypeService;

    @Autowired
    private StaffService staffService;

    @GetMapping("/material-purchases")
    public String viewPurchases(Model model) {
        List<MaterialType> materialTypes = materialTypeService.getAllMaterialTypes();

        Map<Integer, MaterialType> materialTypeMap = new HashMap<>();
        for (MaterialType mt : materialTypes) {
            materialTypeMap.put(mt.getMaterialTypeId(), mt);
        }

        model.addAttribute("purchases", materialPurchaseService.getAllMaterialPurchase());
        model.addAttribute("materialTypes", materialTypes);
        model.addAttribute("materialTypeMap", materialTypeMap);
        model.addAttribute("staffList", staffService.getAllStaff());
        model.addAttribute("newPurchase", new MaterialPurchase());
        return "material-purchases";
    }

    @PostMapping("/material-purchases")
    public String addPurchase(@ModelAttribute MaterialPurchase newPurchase) {
        materialPurchaseService.saveMaterialPurchase(newPurchase);
        return "redirect:/material-purchases";
    }


}