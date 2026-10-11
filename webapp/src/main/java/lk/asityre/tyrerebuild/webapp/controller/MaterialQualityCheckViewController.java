package lk.asityre.tyrerebuild.webapp.controller;

import jakarta.servlet.http.HttpSession;
import lk.asityre.tyrerebuild.webapp.service.MaterialQualityCheckService;
import lk.asityre.tyrerebuild.webapp.service.MaterialTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MaterialQualityCheckViewController {

    @Autowired private MaterialQualityCheckService checkService;
    @Autowired private MaterialTypeService materialTypeService;

    @GetMapping("/sourcing/quality-checks")
    public String page(Model model) {
        model.addAttribute("awaiting", checkService.getAwaitingCheck());
        model.addAttribute("checks", checkService.getAll());
        model.addAttribute("materialTypes", materialTypeService.getAllMaterialTypes());
        return "quality-checks";
    }

    @PostMapping("/sourcing/quality-checks/save")
    public String save(@RequestParam Integer purchaseId,
                       @RequestParam(required = false) String qualityGrade,
                       @RequestParam String result,
                       @RequestParam(required = false) String remarks,
                       HttpSession session,
                       RedirectAttributes ra) {

        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        try {
            checkService.recordCheck(purchaseId, qualityGrade, result, remarks, userId);
            ra.addFlashAttribute("success",
                    "Passed".equals(result) ? "Passed. Stock added." : "Failed. Purchase stays Pending.");
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/sourcing/quality-checks";
    }
}