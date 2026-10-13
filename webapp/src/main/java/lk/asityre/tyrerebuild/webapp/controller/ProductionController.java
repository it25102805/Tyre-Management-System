package lk.asityre.tyrerebuild.webapp.controller;

import lk.asityre.tyrerebuild.webapp.model.Order;
import lk.asityre.tyrerebuild.webapp.model.Tyre;
import lk.asityre.tyrerebuild.webapp.model.User;
import lk.asityre.tyrerebuild.webapp.repository.UserRepository;
import lk.asityre.tyrerebuild.webapp.service.ProductionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

        List<Order> orders = productionService.getAllOrders();
        long pendingCount = orders.stream()
                .filter(o -> o.getOrderStatus() == Order.OrderStatus.PENDING)
                .count();

        model.addAttribute("tyres", productionService.getAllTyres());
        model.addAttribute("orders", orders);
        model.addAttribute("pendingCount", pendingCount);
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
        try {
            Tyre.RebuildStage enumStage = Tyre.RebuildStage.valueOf(stage.toUpperCase());
            productionService.updateStage(tyreId, enumStage);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid stage passed: " + stage);
        }
        return "redirect:/production";
    }

    @PostMapping("/orders/status")
    public String updateOrderStatus(@RequestParam Integer orderId, @RequestParam String status, RedirectAttributes redirect) {
        try {
            Order.OrderStatus newStatus = Order.OrderStatus.valueOf(status.toUpperCase());
            if (productionService.updateOrderStatus(orderId, newStatus)) {
                redirect.addFlashAttribute("success", "Order #" + orderId + " updated to " + newStatus + ".");
            } else {
                redirect.addFlashAttribute("error", "Order #" + orderId + " was not found.");
            }
        } catch (IllegalArgumentException e) {
            redirect.addFlashAttribute("error", "Invalid status: " + status);
        }
        return "redirect:/production";
    }
}