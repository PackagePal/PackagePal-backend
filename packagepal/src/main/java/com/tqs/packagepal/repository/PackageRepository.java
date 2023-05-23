package com.tqs.packagepal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tqs.packagepal.model.Package;
import com.tqs.packagepal.model.PickupPoint;
import com.tqs.packagepal.model.Store;

@Repository
public interface PackageRepository extends JpaRepository<Package,Long>{
    Optional<Package> findByPackageId(String packageId);
    List<Package> findByPickupPoint(PickupPoint pickupPoint);
    List<Package> findByStore(Store store);
    List<Package> findByUserEmail(String userEmail);
}