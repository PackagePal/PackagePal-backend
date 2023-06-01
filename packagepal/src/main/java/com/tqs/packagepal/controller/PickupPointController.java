package com.tqs.packagepal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import com.tqs.packagepal.model.PickupPoint;
import com.tqs.packagepal.service.PickupPointService;

@RestController
@CrossOrigin(origins={"http://192.168.160.234:8080", "http://192.168.160.234:3000/",
                        "http://192.168.160.234:8080", "http://192.168.160.234:3001/"})
@RequestMapping("/api/v1/pickuppoints/")
public class PickupPointController {
    
    @Autowired
    private PickupPointService pickupPointService;


    @GetMapping
    public ResponseEntity<List<PickupPoint>> getAllPickupPoints() {
        List<PickupPoint> pickupPoints = pickupPointService.getAllPickupPoints();
        return ResponseEntity.ok(pickupPoints);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PickupPoint> getPickupPointById(@PathVariable Long id) {
        return pickupPointService.getPickupPointById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<Optional<PickupPoint>> getPickupPointsByCity(@PathVariable String city) {
        Optional<PickupPoint> pickupPoints = pickupPointService.getPickupPointsByCity(city);
        return ResponseEntity.ok(pickupPoints);
    }

    @GetMapping("/postalcode/{postalcode}")
    public ResponseEntity<Optional<PickupPoint>> getPickupPointsByPostalCode(@PathVariable String postalcode) {
        Optional<PickupPoint> pickupPoints = pickupPointService.getPickupPointsByPostalCode(postalcode);
        return ResponseEntity.ok(pickupPoints);
    }

    @PostMapping
    public ResponseEntity<PickupPoint> addPickupPoint(@RequestBody PickupPoint pickupPoint) {
        PickupPoint addedPickupPoint = pickupPointService.addPickupPoint(pickupPoint);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedPickupPoint);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePickupPointById(@PathVariable Long id) {
        pickupPointService.deletePickupPointById(id);
        return ResponseEntity.noContent().build();
    }
}
