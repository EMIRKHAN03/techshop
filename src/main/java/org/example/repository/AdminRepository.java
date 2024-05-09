package org.example.repository;


import org.example.model.Admin;

import java.sql.*;


public class AdminRepository {
    private Connection connection;

    public AdminRepository(Connection connection) {
        this.connection = connection;
    }

    public void addAdmin(Admin admin) throws SQLException {
        String query = "INSERT INTO admins (first_name, last_name, login, password) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, admin.getFirstName());
            statement.setString(2, admin.getLastName());
            statement.setString(3, admin.getLogin());
            statement.setString(4, admin.getPassword());
            statement.executeUpdate();
        }
    }

    public Admin authenticateAdmin(String login, String password) throws SQLException {
        String query = "SELECT * FROM admins WHERE login = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Admin admin = new Admin();
                admin.setId(resultSet.getInt("id"));
                admin.setFirstName(resultSet.getString("first_name"));
                admin.setLastName(resultSet.getString("last_name"));
                admin.setLogin(resultSet.getString("login"));
                admin.setPassword(resultSet.getString("password"));
                return admin;
            }
            return null;
        }
    }
}
