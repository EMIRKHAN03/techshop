package org.example.repository;



import org.example.model.CartItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CartRepository {
    private Connection connection;

    public CartRepository(Connection connection) {
        this.connection = connection;
    }

    // Добавление товара в корзину
    public void addToCart(CartItem cartItem) throws SQLException {
        String query = "INSERT INTO cart_items (client_id, product_id, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, cartItem.getClientId());
            statement.setInt(2, cartItem.getProductId());
            statement.setInt(3, cartItem.getQuantity());
            statement.executeUpdate();
        }
    }

    // Обновление количества товара в корзине
    public void updateCartItemQuantity(int cartItemId, int quantity) throws SQLException {
        String query = "UPDATE cart_items SET quantity = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, quantity);
            statement.setInt(2, cartItemId);
            statement.executeUpdate();
        }
    }

    // Удаление товара из корзины
    public void deleteCartItem(int cartItemId) throws SQLException {
        String query = "DELETE FROM cart_items WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, cartItemId);
            statement.executeUpdate();
        }
    }

    // Получение всех товаров в корзине для клиента
    public List<CartItem> getAllCartItemsForClient(int clientId) throws SQLException {
        List<CartItem> cartItems = new ArrayList<>();
        String query = "SELECT * FROM cart_items WHERE client_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, clientId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CartItem cartItem = new CartItem();
                cartItem.setId(resultSet.getInt("id"));
                cartItem.setClientId(resultSet.getInt("client_id"));
                cartItem.setProductId(resultSet.getInt("product_id"));
                cartItem.setQuantity(resultSet.getInt("quantity"));
                cartItems.add(cartItem);
            }
        }
        return cartItems;
    }
}
