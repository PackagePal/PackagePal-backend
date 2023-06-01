package com.tqs.packagepal.Repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.tqs.packagepal.model.Admin;
import com.tqs.packagepal.repository.AdminRepository;

@DataJpaTest
class AdminRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private AdminRepository adminRepository;

    private ArrayList<Admin> Admins;
    private Admin Admin1;
    private Admin Admin2;

    @BeforeEach
    void setUp() {
        Admins = new ArrayList<>();
        Admin1 = new Admin("name1", "email1@ua.pt", "pwd1");
        Admin2 = new Admin("name2", "email2@ua.pt", "pwd2");

        Admins.add(Admin1);
        Admins.add(Admin2);

        testEntityManager.persistAndFlush(Admin1);
        testEntityManager.persistAndFlush(Admin2);
    }

    @Test
    void whenGetAdmins_thenReturnAllAdmins() {
        List<Admin> returnedAdmins = adminRepository.findAll();

        assertThat(returnedAdmins).containsExactlyInAnyOrderElementsOf(Admins);
    }

    @Test
    void whenGetAdminById_thenReturnAdmin() {
        Optional<Admin> returnedAdminOptional = adminRepository.findById(Admin1.getId());
        Admin returnedAdmin = returnedAdminOptional.orElse(null);

        assertEquals(Admin1, returnedAdmin);
    }

    @Test
    void whenGetAdminByInvalidId_thenReturnNull() {
        Long invalidId = 200L;
        Optional<Admin> returnedAdminOptional = adminRepository.findById(invalidId);
        Admin returnedAdmin = returnedAdminOptional.orElse(null);

        assertNull(returnedAdmin);
    }

    @Test
    void whenGetAdminByEmail_thenReturnAdmin() {
        Optional<Admin> returnedAdminOptional = adminRepository.findByEmail(Admin1.getEmail());
        Admin returnedAdmin = returnedAdminOptional.orElse(null);

        assertEquals(Admin1, returnedAdmin);
    }

    @Test
    void whenGetAdminByInvalidEmail_thenReturnNull() {
        String invalidEmail = "some email";
        Optional<Admin> returnedAdminOptional = adminRepository.findByEmail(invalidEmail);
        Admin returnedAdmin = returnedAdminOptional.orElse(null);

        assertNull(returnedAdmin);
    }

    @Test
    void whenGetAdminByUsername_thenReturnAdmin() {
        Optional<Admin> returnedAdminOptional = adminRepository.findByUsername(Admin1.getUsername());
        Admin returnedAdmin = returnedAdminOptional.orElse(null);

        assertEquals(Admin1, returnedAdmin);
    }

    @Test
    void whenGetAdminByInvalidUsername_thenReturnNull() {
        String invalidUsername = "some username";
        Optional<Admin> returnedAdminOptional = adminRepository.findByUsername(invalidUsername);
        Admin returnedAdmin = returnedAdminOptional.orElse(null);

        assertNull(returnedAdmin);
    }
}