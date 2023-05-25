package com.tqs.packagepal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.tqs.packagepal.model.Admin;
import com.tqs.packagepal.repository.AdminRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Admin addAdmin(Admin admin) {
        Optional<Admin> adminOptional = adminRepository.findByEmail(admin.getEmail());
        if (adminOptional.isPresent()) {
            throw new IllegalStateException("Email already taken");
        }
        adminOptional = adminRepository.findByUsername(admin.getUsername());
        if (adminOptional.isPresent()) {
            throw new IllegalStateException("Username already taken");
        }
        return adminRepository.save(admin);
    }

    public void deleteAdmin(Admin admin) {
        adminRepository.delete(admin);
    }

    public Optional<Admin> getAdminByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    public Optional<Admin> getAdminByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

}
