package com.tqs.packagepal.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tqs.packagepal.model.Admin;
import com.tqs.packagepal.repository.AdminRepository;
import com.tqs.packagepal.service.AdminService;

class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminService adminService;

    private List<Admin> dummyAdmins;
    private Admin dummyAdmin1;
    private Admin dummyAdmin2;

    @BeforeEach
    void setUp() {
        dummyAdmins = new ArrayList<>();
        dummyAdmin1 = new Admin("name1", "email1", "pwd1");
        dummyAdmin2 = new Admin("name2", "email2", "pwd2");

        dummyAdmins.add(dummyAdmin1);
        dummyAdmins.add(dummyAdmin2);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenGetAllAdmins_thenReturnAllAdmins() {
        when(adminRepository.findAll()).thenReturn(dummyAdmins);

        List<Admin> returnedAdmins = adminService.getAllAdmins();

        assertEquals(dummyAdmins, returnedAdmins);
        verify(adminRepository, times(1)).findAll();
    }

    @Test
    void whenAddValidAdmin_thenReturnCreatedAdmin() {
        when(adminRepository.findByEmail(dummyAdmin1.getEmail())).thenReturn(Optional.empty());
        when(adminRepository.findByUsername(dummyAdmin1.getUsername())).thenReturn(Optional.empty());
        when(adminRepository.save(dummyAdmin1)).thenReturn(dummyAdmin1);

        Admin createdAdmin = adminService.addAdmin(dummyAdmin1);

        assertEquals(dummyAdmin1, createdAdmin);
        verify(adminRepository, times(1)).findByEmail(dummyAdmin1.getEmail());
        verify(adminRepository, times(1)).findByUsername(dummyAdmin1.getUsername());
        verify(adminRepository, times(1)).save(dummyAdmin1);
    }

    @Test
    void whenAddAdminWithExistingEmail_thenThrowException() {
        when(adminRepository.findByEmail(dummyAdmin1.getEmail())).thenReturn(Optional.of(dummyAdmin1));

        Assertions.assertThrows(IllegalStateException.class, () -> {
            adminService.addAdmin(dummyAdmin1);
        });

        verify(adminRepository, times(1)).findByEmail(dummyAdmin1.getEmail());
        verify(adminRepository, times(0)).findByUsername(dummyAdmin1.getUsername());
        verify(adminRepository, times(0)).save(dummyAdmin1);
    }

    @Test
    void whenAddAdminWithExistingUsername_thenThrowException() {
        when(adminRepository.findByEmail(dummyAdmin1.getEmail())).thenReturn(Optional.empty());
        when(adminRepository.findByUsername(dummyAdmin1.getUsername())).thenReturn(Optional.of(dummyAdmin1));

        Assertions.assertThrows(IllegalStateException.class, () -> {
            adminService.addAdmin(dummyAdmin1);
        });

        verify(adminRepository, times(1)).findByEmail(dummyAdmin1.getEmail());
        verify(adminRepository, times(1)).findByUsername(dummyAdmin1.getUsername());
        verify(adminRepository, times(0)).save(dummyAdmin1);
    }

    @Test
    void whenDeleteAdmin_thenVerifyDeletion() {
        adminService.deleteAdmin(dummyAdmin1);

        verify(adminRepository, times(1)).delete(dummyAdmin1);
    }

    @Test
    void whenGetAdminByEmail_thenReturnAdmin() {
        when(adminRepository.findByEmail(dummyAdmin1.getEmail())).thenReturn(Optional.of(dummyAdmin1));

        Optional<Admin> returnedAdmin = adminService.getAdminByEmail(dummyAdmin1.getEmail());

        assertEquals(dummyAdmin1, returnedAdmin.orElse(null));
        verify(adminRepository, times(1)).findByEmail(dummyAdmin1.getEmail());
    }

    @Test
    void whenGetAdminByInvalidEmail_thenReturnNull() {
        String invalidEmail = "invalid_email@example.com";

        when(adminRepository.findByEmail(invalidEmail)).thenReturn(Optional.empty());

        Optional<Admin> returnedAdmin = adminService.getAdminByEmail(invalidEmail);

        assertNull(returnedAdmin.orElse(null));
        verify(adminRepository, times(1)).findByEmail(invalidEmail);
    }

    @Test
    void whenGetAdminByUsername_thenReturnAdmin() {
        when(adminRepository.findByUsername(dummyAdmin1.getUsername())).thenReturn(Optional.of(dummyAdmin1));

        Optional<Admin> returnedAdmin = adminService.getAdminByUsername(dummyAdmin1.getUsername());

        assertEquals(dummyAdmin1, returnedAdmin.orElse(null));
        verify(adminRepository, times(1)).findByUsername(dummyAdmin1.getUsername());
    }

    @Test
    void whenGetAdminByInvalidUsername_thenReturnNull() {
        String invalidUsername = "invalid_username";

        when(adminRepository.findByUsername(invalidUsername)).thenReturn(Optional.empty());

        Optional<Admin> returnedAdmin = adminService.getAdminByUsername(invalidUsername);

        assertNull(returnedAdmin.orElse(null));
        verify(adminRepository, times(1)).findByUsername(invalidUsername);
    }

}