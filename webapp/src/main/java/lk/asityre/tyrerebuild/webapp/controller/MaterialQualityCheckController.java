package lk.asityre.tyrerebuild.webapp.controller;
import jakarta.servlet.http.HttpSession;
import lk.asityre.tyrerebuild.webapp.service.MaterialQualityCheckService;
import lk.asityre.tyrerebuild.webapp.service.MaterialTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class MaterialQualityCheckController {
    @Autowired
    private MaterialQualityCheckService checkService;
    @Autowired
    private MaterialTypeService materialTypeService;

    // Display the quality checks page
    @GetMapping("/sourcing/quality-checks")
    public String qualityChecksPage(Model model) {
        model.addAttribute("awaiting", checkService.getAwaitingCheck());
        model.addAttribute("checks", checkService.getAll());
        model.addAttribute("materialTypes",
                materialTypeService.getAllMaterialTypes());
        return "quality-checks";
    }
    // Save a quality check
    @PostMapping("/sourcing/quality-checks/save")
    public String saveQualityCheck(
            @RequestParam Integer purchaseId,
            @RequestParam(required = false) String qualityGrade,
            @RequestParam String result,
            @RequestParam(required = false) String remarks,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        try {
            checkService.recordCheck(purchaseId, qualityGrade, result, remarks, userId);
            if ("Passed".equals(result)) {
                redirectAttributes.addFlashAttribute(
                        "success", "Passed. Stock added.");
            } else {
                redirectAttributes.addFlashAttribute(
                        "success", "Failed. Purchase stays Pending.");
            }
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute(
                    "error", e.getMessage());
        }
        return "redirect:/sourcing/quality-checks";
    }
}