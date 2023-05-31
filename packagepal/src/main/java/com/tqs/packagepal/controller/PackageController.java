package com.tqs.packagepal.controller;

import com.tqs.packagepal.model.DeliveryStatus;
import com.tqs.packagepal.model.Package;
import com.tqs.packagepal.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins={"http://localhost:3000", "http://127.0.0.1:3000"})
@RequestMapping("/api/v1/packages/")
public class PackageController {

    @Autowired
    private PackageService packageService;

    @GetMapping
    public ResponseEntity<List<Package>> getAllPackages() {
        List<Package> packages = packageService.getAllPackages();
        return ResponseEntity.ok(packages);
    }
    
    @GetMapping("/{packageId}")
    public ResponseEntity<Package> getPackageById(@PathVariable("packageId") String packageId) {
        Optional<Package> pack = packageService.findPackageById(packageId);
        if(pack.isPresent()){
            return ResponseEntity.ok(pack.get());
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<List<Package>> getPackagesByUserEmail(@PathVariable("email") String userEmail) {
        List<Package> packages = packageService.findPackagesByUserEmail(userEmail);
        if (!packages.isEmpty()) {
            return ResponseEntity.ok(packages);
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{packageId}/status")
    public ResponseEntity<Package> updatePackageStatus(
        @PathVariable("packageId") String packageId,
        @RequestBody DeliveryStatus newStatus
    ) {
        Optional<Package> optionalPackage = packageService.findPackageById(packageId);
        if (optionalPackage.isPresent()) {
            Package pack = optionalPackage.get();
            pack.setStatus(newStatus);
            Package updatedPackage = packageService.addPackage(pack);
            return ResponseEntity.ok(updatedPackage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Package> createPackage(@RequestBody Package pack) {
        Package createdPackage = packageService.addPackage(pack);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPackage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePackage(@PathVariable("id") String packageId) {
        boolean deleted = packageService.deletePackage(packageId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
