package lk.asityre.tyrerebuild.webapp.service;

import lk.asityre.tyrerebuild.webapp.model.Order;
import lk.asityre.tyrerebuild.webapp.model.Tyre;
import lk.asityre.tyrerebuild.webapp.repository.OrderRepository;
import lk.asityre.tyrerebuild.webapp.repository.TyreRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProductionService {

    private final TyreRepository tyreRepository;
    private final OrderRepository orderRepository;

    public ProductionService(TyreRepository tyreRepository, OrderRepository orderRepository) {
        this.tyreRepository = tyreRepository;
        this.orderRepository = orderRepository;
    }


    public List<Tyre> getAllTyres() {
        return tyreRepository.findAllByOrderByTyreIdDesc();
    }

    public Tyre getTyre(Integer id) {
        return tyreRepository.findById(id).orElse(null);
    }

    public void updateStage(Integer tyreId, Tyre.RebuildStage stage) {
        Tyre tyre = getTyre(tyreId);
        if (tyre == null) {
            return;
        }

        tyre.setRebuildStage(stage);

        if (stage == Tyre.RebuildStage.COMPLETED) {
            tyre.setCompletedDate(LocalDate.now());
        }

        tyreRepository.save(tyre);
    }

    public void updateStage(Integer tyreId, String stage) {
        if (stage != null) {
            updateStage(tyreId, Tyre.RebuildStage.valueOf(stage.toUpperCase()));
        }
    }

    public void saveTyre(Tyre tyre) {
        if (tyre.getReceivedDate() == null) {
            tyre.setReceivedDate(LocalDate.now());
        }

        if (tyre.getRebuildStage() == null) {
            tyre.setRebuildStage(Tyre.RebuildStage.RECEIVED);
        }

        tyreRepository.save(tyre);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAllByOrderByOrderIdDesc();
    }

    public boolean updateOrderStatus(Integer orderId, Order.OrderStatus status) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            return false;
        }
        order.setOrderStatus(status);
        orderRepository.save(order);
        return true;
    }
}