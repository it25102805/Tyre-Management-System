package lk.asityre.tyrerebuild.webapp.controller;

import lk.asityre.tyrerebuild.webapp.model.MaterialPurchase;
import lk.asityre.tyrerebuild.webapp.service.MaterialPurchaseService;
import lk.asityre.tyrerebuild.webapp.service.MaterialTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

@Controller
public class MaterialPurchaseViewController {

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

        // STAFF LIST GOES HERE (needs your Staff service, see below)

        return "purchases";
    }

    @PostMapping("/sourcing/purchases/save")
    public String savePurchase(
            @ModelAttribute MaterialPurchase materialPurchase,
            HttpSession session) {

        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        materialPurchaseService.saveMaterialPurchase(
                materialPurchase,
                userId
        );

        return "redirect:/sourcing/purchases";
    }

    @PostMapping("/sourcing/purchases/{id}/status")
    public String changeStatus(@PathVariable Integer id,
                               @RequestParam String status) {

        materialPurchaseService.updatePurchaseStatus(id, status);
        return "redirect:/sourcing/purchases";
    }
}