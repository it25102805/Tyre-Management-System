package lk.asityre.tyrerebuild.webapp.controller;

import jakarta.servlet.http.HttpSession;
import lk.asityre.tyrerebuild.webapp.model.Order;
import lk.asityre.tyrerebuild.webapp.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String viewOrders(Model model, HttpSession session) {
        if (session.getAttribute("userId") == null) return "redirect:/login";
        model.addAttribute("orders", orderService.getAllOrders());
        return "orders";
    }

    @PostMapping("/create")
    public String createOrder(@RequestParam String tyreSize,
                              @RequestParam Integer quantity,
                              @RequestParam String requiredDate,
                              HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            orderService.createOrder(userId, tyreSize, quantity, LocalDate.parse(requiredDate));
        }
        return "redirect:/orders";
    }

    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable Integer id, @RequestParam String status) {
        orderService.updateOrderStatus(id, Order.OrderStatus.valueOf(status.toUpperCase()));
        return "redirect:/orders";
    }
}