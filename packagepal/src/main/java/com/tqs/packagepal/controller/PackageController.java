package com.tqs.packagepal.controller;

import com.tqs.packagepal.model.Package;
import com.tqs.packagepal.model.PickupPoint;
import com.tqs.packagepal.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import com.tqs.packagepal.service.PickupPointService;

@RestController
@CrossOrigin(origins={"http://localhost:3000", "http://127.0.0.1:3000"})
@RequestMapping("/api/v1/packages/")
public class PackageController {

    @Autowired
    private PackageService packageService;

    @Autowired
    private PickupPointService pickupPointService;

    @GetMapping
    public ResponseEntity<List<Package>> getAllPackages() {
        List<Package> packages = packageService.getAllPackages();
        return ResponseEntity.ok().body(packages);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Package> getPackageById(@PathVariable Long id) {
        Optional<Package> packages = packageService.findPackageById(Long.valueOf(id));
        if(packages.isPresent()){
            return ResponseEntity.ok().body(packages.get());
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pickuppoint/{id}")
    public ResponseEntity<List<Package>> getPackagesByPickupPoint(@PathVariable Long id) {
        Optional<PickupPoint> pickupPoint = pickupPointService.getPickupPointById(id);
        if(pickupPoint.isPresent()){
            List<Package> packages = packageService.findPackagesByPickupPoint(pickupPoint.get());
            return ResponseEntity.ok().body(packages);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Package> createPackage(@RequestBody Package pack) {
        Package createdPackage = packageService.savePackage(pack);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPackage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePackage(@PathVariable Long id) {
        Optional<Package> packageOptional = packageService.findPackageById(id);
        if (packageOptional.isPresent()) {
            packageService.deletePackage(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
