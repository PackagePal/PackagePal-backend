package com.tqs.packagepal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tqs.packagepal.model.Package;
import com.tqs.packagepal.model.DeliveryStatus;

@Repository
public interface PackageRepository extends JpaRepository<Package,Long>{
    Optional<Package> findByPackageId(String packageId);
    List<Package> findByUserEmail(String userEmail);    
    
}