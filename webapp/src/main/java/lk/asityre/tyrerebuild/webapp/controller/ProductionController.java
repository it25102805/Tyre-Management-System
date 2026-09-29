package lk.asityre.tyrerebuild.webapp.controller;

import lk.asityre.tyrerebuild.webapp.model.Tyre;
import lk.asityre.tyrerebuild.webapp.service.ProductionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/production")
public class    ProductionController {

    private final ProductionService productionService;

    public ProductionController(ProductionService productionService) {
        this.productionService = productionService;
    }


    // Open production dashboard
    @GetMapping
    public String productionDashboard(Model model) {

        model.addAttribute(
                "tyres",
                productionService.getAllTyres()
        );

        model.addAttribute(
                "newTyre",
                new Tyre()
        );

        return "production";
    }


    // Add new tyre
    @PostMapping("/add")
    public String addTyre(@ModelAttribute Tyre tyre) {

        productionService.saveTyre(tyre);

        return "redirect:/production";
    }


    // Update rebuild stage
    @PostMapping("/update-stage")
    public String updateStage(
            @RequestParam Integer tyreId,
            @RequestParam String stage) {

        productionService.updateStage(
                tyreId,
                stage
        );

        return "redirect:/production";
    }
}