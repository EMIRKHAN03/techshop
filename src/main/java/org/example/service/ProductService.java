package org.example.service;



import org.example.model.Product;
import org.example.repository.ProductRepository;

import java.sql.SQLException;
import java.util.List;


public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProduct(Product product) throws SQLException {
        productRepository.addProduct(product);
    }

    public void updateProduct(Product product) throws SQLException {
        productRepository.updateProduct(product);
    }

    public void deleteProduct(int productId) throws SQLException {
        productRepository.deleteProduct(productId);
    }

    public List<Product> getAllProducts() throws SQLException {
        return productRepository.getAllProducts();
    }

    public Product getProductById(int productId) throws SQLException {
        return productRepository.getProductById(productId);
    }
}
