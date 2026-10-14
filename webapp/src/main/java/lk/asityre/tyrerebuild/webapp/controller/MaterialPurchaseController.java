package lk.asityre.tyrerebuild.webapp.controller;
import lk.asityre.tyrerebuild.webapp.model.MaterialPurchase;
import lk.asityre.tyrerebuild.webapp.service.MaterialPurchaseService;
import lk.asityre.tyrerebuild.webapp.service.MaterialTypeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MaterialPurchaseController {
    @Autowired
    private MaterialPurchaseService materialPurchaseService;
    @Autowired
    private MaterialTypeService materialTypeService;

    @GetMapping("/sourcing/purchases")
    public String purchasesPage(Model model) {
        model.addAttribute("purchases",
                materialPurchaseService.getPurchases(null));
        model.addAttribute("materialTypes",
                materialTypeService.getAllMaterialTypes());
        return "purchases";
    }

    @PostMapping("/sourcing/purchases/save")
    public String savePurchase(@ModelAttribute MaterialPurchase materialPurchase, HttpSession session, RedirectAttributes redirectAttributes) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        try {
            materialPurchaseService.saveMaterialPurchase(materialPurchase, userId);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/sourcing/purchases";
    }

    @PostMapping("/sourcing/purchases/{id}/status")
    public String changeStatus(@PathVariable Integer id, @RequestParam String status, RedirectAttributes redirectAttributes) {
        try {
            materialPurchaseService.updatePurchaseStatus(id, status);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/sourcing/purchases";
    }
}