package lk.asityre.tyrerebuild.webapp.controller;

import lk.asityre.tyrerebuild.webapp.model.Orders;
import lk.asityre.tyrerebuild.webapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class OrderViewController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public String viewOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("newOrder", new Orders());
        return "orders";
    }

    @PostMapping("/orders")
    public String addOrder(@ModelAttribute Orders newOrder) {
        // You must use the lowercase variable "orderService" injected by Spring
        orderService.saveOrder(newOrder);
        return "redirect:/orders";
    }

    @PostMapping("/orders/delete/{id}")
    public String deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }
}