package org.example.repository;

import org.example.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class OrderRepository {
    private Connection connection;

    public OrderRepository(Connection connection) {
        this.connection = connection;
    }

    public void addOrder(Order order) throws SQLException {
        String query = "INSERT INTO orders (client_id, product_id, quantity, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, order.getClientId());
            statement.setInt(2, order.getProductId());
            statement.setInt(3, order.getQuantity());
            statement.setString(4, order.getStatus());
            statement.executeUpdate();
        }
    }

    public void updateOrderStatus(int orderId, String status) throws SQLException {
        String query = "UPDATE orders SET status = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, status);
            statement.setInt(2, orderId);
            statement.executeUpdate();
        }
    }

    public List<Order> getAllOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt("id"));
                order.setClientId(resultSet.getInt("client_id"));
                order.setProductId(resultSet.getInt("product_id"));
                order.setQuantity(resultSet.getInt("quantity"));
                order.setStatus(resultSet.getString("status"));
                orders.add(order);
            }
        }
        return orders;
    }
    public List<Order> getOrdersForClient(int clientId) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE client_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, clientId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt("id"));
                order.setClientId(resultSet.getInt("client_id"));
                order.setProductId(resultSet.getInt("product_id"));
                order.setQuantity(resultSet.getInt("quantity"));
                order.setStatus(resultSet.getString("status"));
                orders.add(order);
            }
        }
        return orders;
    }
}
