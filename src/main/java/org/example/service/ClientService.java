package org.example.service;




import org.example.model.Client;
import org.example.repository.ClientRepository;

import java.sql.SQLException;
import java.util.List;

public class ClientService {
    private ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void registerClient(Client client) throws SQLException {
        clientRepository.addClient(client);
    }

    public Client loginClient(String login, String password) throws SQLException {
        return clientRepository.authenticateClient(login, password);
    }

    public List<Client> getAllClients() throws SQLException {
        return clientRepository.getAllClients();

    }

    public void updateClient(Client client) throws SQLException {
        clientRepository.updateClient(client);
    }
}
