package lk.asityre.tyrerebuild.webapp.controller;

import lk.asityre.tyrerebuild.webapp.model.MaterialQualityCheck;
import lk.asityre.tyrerebuild.webapp.service.MaterialQualityCheckService;
import lk.asityre.tyrerebuild.webapp.service.MaterialPurchaseService;
import lk.asityre.tyrerebuild.webapp.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MaterialQualityCheckViewController {

    @Autowired
    private MaterialQualityCheckService mqcService;

    @Autowired
    private MaterialPurchaseService materialPurchaseService; // For purchase dropdown

    @Autowired
    private StaffService staffService; // For staff/checker dropdown

    @GetMapping("/material-quality-checks")
    public String viewQualityChecks(Model model) {
        model.addAttribute("qualityChecks", mqcService.getAllChecks());
        model.addAttribute("purchases", materialPurchaseService.getAllMaterialPurchase());
        model.addAttribute("staffList", staffService.getAllStaff());
        model.addAttribute("newQualityCheck", new MaterialQualityCheck());
        return "material-quality-checks"; // Expects a material-quality-checks.html template
    }

    @PostMapping("/material-quality-checks")
    public String saveQualityCheck(@ModelAttribute("newQualityCheck") MaterialQualityCheck check) {
        if (check.getMaterialCheckId() == null) {
            mqcService.saveCheck(check);
        } else {
            mqcService.updateCheck(check.getMaterialCheckId(), check);
        }
        return "redirect:/material-quality-checks";
    }

    @PostMapping("/material-quality-checks/delete/{id}")
    public String deleteQualityCheck(@PathVariable Integer id) {
        mqcService.deleteCheck(id);
        return "redirect:/material-quality-checks";
    }
}