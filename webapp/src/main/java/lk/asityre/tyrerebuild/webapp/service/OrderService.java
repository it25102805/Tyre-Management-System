package lk.asityre.tyrerebuild.webapp.service;

import lk.asityre.tyrerebuild.webapp.model.Order;
import lk.asityre.tyrerebuild.webapp.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order createOrder(Integer customerId, String tyreSize, Integer quantity, LocalDate requiredDate) {
        Order order = new Order();
        order.setCustomerId(customerId);
        order.setOrderDate(LocalDate.now());
        order.setRequiredDate(requiredDate);
        order.setTyreSize(tyreSize);
        order.setQuantity(quantity);
        order.setOrderStatus(Order.OrderStatus.PENDING);
        return orderRepository.save(order);
    }

    public void updateOrderStatus(Integer orderId, Order.OrderStatus status) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setOrderStatus(status);
        orderRepository.save(order);
    }
}