package org.example.service;


import org.example.model.Order;
import org.example.repository.OrderRepository;

import java.sql.SQLException;
import java.util.List;


public class OrderService {
    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void addOrder(Order order) throws SQLException {
        orderRepository.addOrder(order);
    }

    public void updateOrderStatus(int orderId, String status) throws SQLException {
        orderRepository.updateOrderStatus(orderId, status);
    }

    public List<Order> getAllOrders() throws SQLException {
        return orderRepository.getAllOrders();
    }

    public List<Order> getOrdersForClient(int id) throws SQLException{
        return orderRepository.getOrdersForClient(id);
    }
}
