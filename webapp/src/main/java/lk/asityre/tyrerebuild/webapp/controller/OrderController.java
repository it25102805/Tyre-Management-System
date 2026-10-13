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
        Integer userId = (Integer) session.getAttribute("userId");
        String role = (String) session.getAttribute("role");
        if (userId == null) {
            return "redirect:/login";
        }
        if ("CUSTOMER".equals(role)) {
            model.addAttribute("orders", orderService.getOrdersByCustomer(userId));
        } else {
            model.addAttribute("orders", orderService.getAllOrders());
        }
        return "orders";
    }
    @PostMapping("/create")
    public String createOrder(@RequestParam String tyreSize,
                              @RequestParam Integer quantity,
                              @RequestParam String requiredDate,
                              HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        String role = (String) session.getAttribute("role");
        if (userId == null) {
            return "redirect:/login";
        }
        if (!"CUSTOMER".equals(role)) {
            return "redirect:/orders";
        }
        orderService.createOrder(userId, tyreSize, quantity, LocalDate.parse(requiredDate));
        return "redirect:/orders";
    }
    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable Integer id, @RequestParam String status, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        String role = (String) session.getAttribute("role");
        if (userId == null) {
            return "redirect:/login";
        }
        if (!"ADMIN".equals(role) && !"MANAGER".equals(role)) {
            return "redirect:/orders";
        }
        try {
            Order.OrderStatus orderStatus = Order.OrderStatus.valueOf(status.toUpperCase());
            orderService.updateOrderStatus(id, orderStatus);
        } catch (IllegalArgumentException e) {
            return "redirect:/orders";
        }
        return "redirect:/orders";
    }

}