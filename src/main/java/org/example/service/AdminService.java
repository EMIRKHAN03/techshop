package org.example.service;



import org.example.model.Admin;
import org.example.repository.AdminRepository;

import java.sql.SQLException;


public class AdminService {
    private AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public void registerAdmin(Admin admin) throws SQLException {
        adminRepository.addAdmin(admin);
    }

    public Admin loginAdmin(String login, String password) throws SQLException {
        return adminRepository.authenticateAdmin(login, password);
    }
}
