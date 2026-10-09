package lk.asityre.tyrerebuild.webapp.controller;

import lk.asityre.tyrerebuild.webapp.model.Tyre;
import lk.asityre.tyrerebuild.webapp.model.User;
import lk.asityre.tyrerebuild.webapp.service.ProductionService;
import lk.asityre.tyrerebuild.webapp.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/production")
public class ProductionController {

    private final ProductionService productionService;
    private final UserRepository userRepository;

    public ProductionController(ProductionService productionService, UserRepository userRepository) {
        this.productionService = productionService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String productionDashboard(Model model) {
        List<User> users = userRepository.findAll();
        Map<Integer, User> userMap = new HashMap<>();
        for (User u : users) {
            userMap.put(u.getUserId(), u);
        }

        model.addAttribute("tyres", productionService.getAllTyres());
        model.addAttribute("userMap", userMap);
        model.addAttribute("newTyre", new Tyre());
        return "production";
    }

    @PostMapping("/add")
    public String addTyre(@ModelAttribute Tyre tyre) {
        productionService.saveTyre(tyre);
        return "redirect:/production";
    }

    @PostMapping("/update-stage")
    public String updateStage(@RequestParam Integer tyreId, @RequestParam String stage) {
        productionService.updateStage(tyreId, stage);
        return "redirect:/production";
    }
}