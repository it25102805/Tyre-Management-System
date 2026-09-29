package lk.asityre.tyrerebuild.webapp.service;

import lk.asityre.tyrerebuild.webapp.model.Orders;
import lk.asityre.tyrerebuild.webapp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    public Orders getOrderById(Integer id) {
        return orderRepository.findById(id).orElse(null);
    }

    public void deleteOrder(Integer id) {
        orderRepository.deleteById(id);
    }

    public Orders updateOrderStatus(Integer id, String newStatus) {
        Orders existingOrder = orderRepository.findById(id).orElse(null);
        if (existingOrder != null) {
            existingOrder.setOrderStatus(newStatus);
            return orderRepository.save(existingOrder);
        }
        return null;
    }

    public Orders updateOrder(Integer id, Orders updatedDetails) {
        Orders existingOrder = orderRepository.findById(id).orElse(null);
        if (existingOrder != null) {
            existingOrder.setQuantity(updatedDetails.getQuantity());
            existingOrder.setRequiredDate(updatedDetails.getRequiredDate());
            existingOrder.setOrderStatus(updatedDetails.getOrderStatus());
            return orderRepository.save(existingOrder);
        }
        return null;
    }

    public Orders saveOrder(Orders order) {
        if (order.getQuantity() == null || order.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        if (order.getCustomerId() == null) {
            throw new IllegalArgumentException("Customer ID is required");
        }
        // tyreId validation removed as the field no longer exists
        return orderRepository.save(order);
    }
}