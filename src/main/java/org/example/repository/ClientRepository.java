package org.example.repository;


import org.example.model.Client;
import org.example.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ClientRepository {
    private Connection connection;

    public ClientRepository(Connection connection) {
        this.connection = connection;
    }

    public void addClient(Client client) throws SQLException {
        String query = "INSERT INTO client (first_name, last_name, address, login, password) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, client.getFirstName());
            statement.setString(2, client.getLastName());
            statement.setString(3, client.getAddress());
            statement.setString(4, client.getLogin());
            statement.setString(5, client.getPassword());
            statement.executeUpdate();
        }
    }

    public Client authenticateClient(String login, String password) throws SQLException {
        String query = "SELECT * FROM client WHERE login = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Client client = new Client();
                client.setId(resultSet.getInt("id"));
                client.setFirstName(resultSet.getString("first_name"));
                client.setLastName(resultSet.getString("last_name"));
                client.setAddress(resultSet.getString("address"));
                client.setLogin(resultSet.getString("login"));
                client.setPassword(resultSet.getString("password"));
                return client;
            }
            return null;
        }
    }

    public List<Client> getAllClients() throws SQLException {
        List<Client> clients=new ArrayList<>();
        String query = "SELECT * FROM client";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Client client=new Client();
                client.setId(resultSet.getInt("id"));
                client.setFirstName(resultSet.getString("first_name"));
                client.setLastName(resultSet.getString("last_name"));
                client.setAddress(resultSet.getString("address"));
                client.setLogin(resultSet.getString("login"));
                clients.add(client);
            }
        }
        return clients;
    }

    public void updateClient(Client client) throws SQLException {
        String query = "UPDATE client SET first_name = ?, last_name = ?, address = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, client.getFirstName());
            statement.setString(2, client.getLastName());
            statement.setString(3, client.getAddress());
            statement.setInt(4, client.getId());
            statement.executeUpdate();
        }
    }
}
