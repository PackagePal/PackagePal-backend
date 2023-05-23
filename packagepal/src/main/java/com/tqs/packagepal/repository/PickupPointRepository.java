package com.tqs.packagepal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tqs.packagepal.model.PickupPoint;

@Repository
public interface PickupPointRepository extends JpaRepository<PickupPoint,Long>{
    Optional<PickupPoint> findByName(String string);
    Optional<PickupPoint> findByEmail(String email);
    Optional<PickupPoint> findByAddress(String address);
}
