package lk.asityre.tyrerebuild.webapp.controller;

import lk.asityre.tyrerebuild.webapp.model.Orders;
import lk.asityre.tyrerebuild.webapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<Orders> getAll() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Orders getById(@PathVariable Integer id) {
        return orderService.getOrderById(id);
    }

    @PostMapping
    public Orders create(@RequestBody Orders order) {
        return orderService.saveOrder(order);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        orderService.deleteOrder(id);
    }

    @PutMapping("/{id}/status")
    public Orders updateStatus(@PathVariable Integer id, @RequestParam String status) {
        return orderService.updateOrderStatus(id, status);
    }

    @PutMapping("/{id}")
    public Orders update(@PathVariable Integer id, @RequestBody Orders orderDetails) {
        return orderService.updateOrder(id, orderDetails);
    }
}