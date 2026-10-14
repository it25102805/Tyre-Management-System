package lk.asityre.tyrerebuild.webapp.controller;

import jakarta.servlet.http.HttpSession;
import lk.asityre.tyrerebuild.webapp.model.MaterialType;
import lk.asityre.tyrerebuild.webapp.model.StockMovement;
import lk.asityre.tyrerebuild.webapp.service.MaterialTypeService;
import lk.asityre.tyrerebuild.webapp.service.StockMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class StockMovementController {

    @Autowired
    private StockMovementService stockMovementService;

    @Autowired
    private MaterialTypeService materialTypeService;

    @GetMapping("/sourcing/stock-movements")
    public String page(Model model) {

        List<MaterialType> types = materialTypeService.getAllMaterialTypes();
        List<StockLevel> levels = new ArrayList<>();
        for (MaterialType type : types) {
            int usable = stockMovementService.getUsableStock(type.getMaterialTypeId());
            int reorder = 0;
            if (type.getReorderLevel() != null) {
                reorder = type.getReorderLevel();
            }

            StockLevel level = new StockLevel(type.getMaterialTypeId(), type.getMaterialName(), type.getUnit(), usable, reorder, usable < reorder);
            levels.add(level);
        }

        int lowCount = 0;
        for (StockLevel level : levels) {
            if (level.low()) {
                lowCount++;
            }
        }

        model.addAttribute("levels", levels);
        model.addAttribute("lowCount", lowCount);
        model.addAttribute("movements", stockMovementService.getAll());
        model.addAttribute("materialTypes", types);
        return "stock-movements";
    }

    @PostMapping("/sourcing/stock-movements/save")
    public String save(@ModelAttribute StockMovement movement, HttpSession session, RedirectAttributes ra) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        try {
            stockMovementService.record(movement, userId);
            ra.addFlashAttribute("success", "Movement recorded.");
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/sourcing/stock-movements";
    }
    public record StockLevel(Integer materialTypeId, String name, String unit, int usable, int reorderLevel, boolean low) {
    }
}