package com.tqs.packagepal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.tqs.packagepal.model.PickupPoint;
import com.tqs.packagepal.repository.PickupPointRepository;

@Service
public class PickupPointService {

    @Autowired
    private PickupPointRepository pickupPointRepository;

    public List<PickupPoint> getAllPickupPoints() {
        return pickupPointRepository.findAll();
    }

    public Optional<PickupPoint> getPickupPointById(Long id) {
        return pickupPointRepository.findById(id);
    }

    public PickupPoint addPickupPoint(PickupPoint pickupPoint) {
        Optional<PickupPoint> pickupPointOptional = pickupPointRepository.findByName(pickupPoint.getName());
        if (pickupPointOptional.isPresent()) {
            throw new IllegalStateException("Pickup Point already exists");
        }
        return pickupPointRepository.save(pickupPoint);
    }

    public void deletePickupPointById(Long id) {
        pickupPointRepository.deleteById(id);
    }

    public Optional<PickupPoint> getPickupPointByName(String name) {
        return pickupPointRepository.findByName(name);
    }

    public Optional<PickupPoint> getPickupPointByEmail(String email) {
        return pickupPointRepository.findByEmail(email);
    }

    public Optional<PickupPoint> getPickupPointByAddress(String address) {
        return pickupPointRepository.findByAddress(address);
    }

}
