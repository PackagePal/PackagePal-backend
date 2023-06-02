package com.tqs.packagepal.controller;

import com.tqs.packagepal.model.DeliveryStatus;
import com.tqs.packagepal.model.Package;
import com.tqs.packagepal.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {
    "http://192.168.160.234:8080", "http://192.168.160.234:3000/",
    "http://192.168.160.234:8080", "http://192.168.160.234:3001/",
    "http://localhost:8080", "http://localhost:3000/",
    "http://localhost:8080", "http://localhost:3001/"
})
@RequestMapping("/api/v1/packages/")
public class PackageController {

    @Autowired
    private PackageService packageService;

    private HttpHeaders createCorsHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("ACCESS", "*");
        return headers;
    }

    @GetMapping
    public ResponseEntity<List<Package>> getAllPackages() {
        List<Package> packages = packageService.getAllPackages();
        return ResponseEntity.ok().headers(createCorsHeaders()).body(packages);
    }

    @GetMapping("/{packageId}")
    public ResponseEntity<Package> getPackageById(@PathVariable("packageId") String packageId) {
        Optional<Package> pack = packageService.findPackageById(packageId);

        if (pack.isPresent()) {
            return ResponseEntity.ok().headers(createCorsHeaders()).body(pack.get());
        }
        return ResponseEntity.noContent().headers(createCorsHeaders()).build();
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<List<Package>> getPackagesByUserEmail(@PathVariable("email") String userEmail) {
        List<Package> packages = packageService.findPackagesByUserEmail(userEmail);
        if (!packages.isEmpty()) {
            return ResponseEntity.ok().headers(createCorsHeaders()).body(packages);
        }
        return ResponseEntity.noContent().headers(createCorsHeaders()).build();
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
            packageService.updatePackageStatus(pack);
            return ResponseEntity.ok().headers(createCorsHeaders()).body(pack);
        } else {
            return ResponseEntity.notFound().headers(createCorsHeaders()).build();
        }
    }

    @PostMapping
    public ResponseEntity<Package> createPackage(@RequestBody Package pack) {
        Package createdPackage = packageService.addPackage(pack);
        return ResponseEntity.status(HttpStatus.CREATED).headers(createCorsHeaders()).body(createdPackage);
    }

    @DeleteMapping("/{packageId}")
    public ResponseEntity<Void> deletePackage(@PathVariable("packageId") String packageId) {
        boolean deleted = packageService.deletePackage(packageId);
        if (deleted) {
            return ResponseEntity.noContent().headers(createCorsHeaders()).build();
        } else {
            return ResponseEntity.notFound().headers(createCorsHeaders()).build();
        }
    }
}
