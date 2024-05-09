package org.example.service;



import org.example.model.CartItem;
import org.example.repository.CartRepository;

import java.sql.SQLException;
import java.util.List;

public class CartService {
    private CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    // Добавление товара в корзину
    public void addToCart(CartItem cartItem) throws SQLException {
        cartRepository.addToCart(cartItem);
    }

    // Обновление количества товара в корзине
    public void updateCartItemQuantity(int cartItemId, int quantity) throws SQLException {
        cartRepository.updateCartItemQuantity(cartItemId, quantity);
    }

    // Удаление товара из корзины
    public void deleteCartItem(int cartItemId) throws SQLException {
        cartRepository.deleteCartItem(cartItemId);
    }

    // Получение всех товаров в корзине для клиента
    public List<CartItem> getAllCartItemsForClient(int clientId) throws SQLException {
        return cartRepository.getAllCartItemsForClient(clientId);
    }
}
