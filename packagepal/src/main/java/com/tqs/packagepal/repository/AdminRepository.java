package com.tqs.packagepal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tqs.packagepal.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long>{
    Optional<Admin> findByEmail(String email);
    Optional<Admin> findByUsername(String username);
    List<Admin> findAll();
}
